package com.wms.service.impl;

import com.wms.domain.*;
import com.wms.mapper.ProductStockMapper;
import com.wms.mapper.SaleAccountMapper;
import com.wms.mapper.StockOutcomeBillItemMapper;
import com.wms.mapper.StockOutcomeBillMapper;
import com.wms.page.PageResult;
import com.wms.query.QueryObject;
import com.wms.service.IStockOutcomeBillService;
import com.wms.util.UserContext;
import com.wms.domain.ProductStock;
import com.wms.domain.SaleAccount;
import com.wms.domain.StockOutcomeBill;
import com.wms.domain.StockOutcomeBillItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

@Service
public class StockOutcomeBillServiceImpl implements IStockOutcomeBillService {

    @Autowired
    private StockOutcomeBillMapper stockOutcomeBillMapper;
    @Autowired
    private StockOutcomeBillItemMapper stockOutcomeBillItemMapper;

    @Autowired
    private SaleAccountMapper saleAccountMapper;

    @Autowired
    private ProductStockMapper productStockMapper;

    public void deleteByPrimaryKey(Long id) {
        //防止外键关系,先删除订单对象
        stockOutcomeBillMapper.deleteByPrimaryKey(id);
        //删除订单对应的明细对象
        stockOutcomeBillItemMapper.deleteBillId(id);
    }

    public void insert(StockOutcomeBill record) {
        //1.设置录入人和录入时间
        System.out.println(record);
        record.setInputUser(UserContext.getCurrentUser());
        record.setInputTime(new Date());
        //设置审核状态
        record.setStatus(StockOutcomeBill.STATUS_NORMAL);
        //2.遍历明细列表,计算总金额与总数量
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalNumber = BigDecimal.ZERO;

        for (StockOutcomeBillItem item : record.getItems()) {
            BigDecimal amount = item.getSalePrice().multiply(item.getNumber()).setScale(2, BigDecimal.ROUND_HALF_UP);
            //3.遍历时计算明细的金额小计
            item.setAmount(amount);

            totalAmount = totalAmount.add(item.getAmount());
            totalNumber = totalNumber.add(item.getNumber());
        }
        record.setTotalAmount(totalAmount);
        record.setTotalNumber(totalNumber);
        //4.保存采购订单
        stockOutcomeBillMapper.insert(record);
        //5.保存明细
        for (StockOutcomeBillItem item : record.getItems()) {
            item.setBillId(record.getId());
            stockOutcomeBillItemMapper.insert(item);
        }
    }

    public StockOutcomeBill selectByPrimaryKey(Long id) {
        return stockOutcomeBillMapper.selectByPrimaryKey(id);
    }

    public List<StockOutcomeBill> selectAll() {
        return stockOutcomeBillMapper.selectAll();
    }

    public PageResult query(QueryObject qo) {

        int rows = stockOutcomeBillMapper.queryForCount(qo);
        if (rows == 0) {
            return PageResult.EMPTY_PAGE;
        }
        List<StockOutcomeBill> list = stockOutcomeBillMapper.queryForList(qo);

        return new PageResult(qo.getCurrentPage(), qo.getPageSize(), rows, list);
    }

    public void updateByPrimaryKey(StockOutcomeBill record) {

        StockOutcomeBill bill = stockOutcomeBillMapper.selectByPrimaryKey(record.getId());

        if (bill.getStatus() == StockOutcomeBill.STATUS_NORMAL) {
            //删除原有的明细
            stockOutcomeBillItemMapper.deleteBillId(record.getId());
            //计算总金额和总数量,并设置到对象中
            BigDecimal totalAmount = BigDecimal.ZERO;
            BigDecimal totalNumber = BigDecimal.ZERO;
            //遍历时计算明细的金额小计
            for (StockOutcomeBillItem item : record.getItems()) {
                BigDecimal amount = item.getSalePrice().multiply(item.getNumber()).setScale(2, RoundingMode.HALF_UP);
                item.setAmount(amount);
                //为明细对象设置订单id
                item.setBillId(record.getId());
                stockOutcomeBillItemMapper.insert(item);

                totalAmount = totalAmount.add(amount);
                totalNumber = totalNumber.add(item.getNumber());
            }
            record.setTotalNumber(totalNumber);
            record.setTotalAmount(totalAmount);
            //更新采购订单
            stockOutcomeBillMapper.updateByPrimaryKey(record);
        }

    }

    public void audit(Long id) {

        StockOutcomeBill bill = stockOutcomeBillMapper.selectByPrimaryKey(id);
        //设置审核人,审核时间,审核状态
        if (bill.getStatus() == StockOutcomeBill.STATUS_NORMAL) {
            System.out.println(UserContext.getCurrentUser().getName());
            bill.setAuditor(UserContext.getCurrentUser());
            bill.setAuditTime(new Date());
            bill.setStatus(StockOutcomeBill.STATUS_AUDITED);

            for (StockOutcomeBillItem item : bill.getItems()) {
                //获取明细对象
                ProductStock ps = productStockMapper.selectByDeptIdAndProductId(bill.getDepot().getId(), item.getProduct().getId());

                if (ps == null) {
                    throw new RuntimeException(item.getProduct().getName() + "货品不存在");
                }
                if (ps.getStoreNumber().compareTo(item.getNumber()) < 0) {
                    throw new RuntimeException(item.getProduct().getName() + "货品在" + bill.getDepot().getName() + "仓库的数量为" + ps.getStoreNumber() + "个,不足出库量" + item.getNumber());
                }
                //库存数量大于销售数量
                //计算库存数量
                ps.setStoreNumber(ps.getStoreNumber().subtract(item.getNumber()));
                //计算库存金额
                ps.setAmount(ps.getPrice().multiply(ps.getStoreNumber()).setScale(2, BigDecimal.ROUND_HALF_UP));

                //更新库存信息
                productStockMapper.updateByPrimaryKey(ps);

                //记录销售帐

                SaleAccount sa = new SaleAccount();
                sa.setVdate(new Date());
                sa.setNumber(item.getNumber());
                sa.setCostPrice(ps.getPrice());
                sa.setCostAmount(sa.getCostPrice().multiply(sa.getNumber()).setScale(2, BigDecimal.ROUND_HALF_UP));
                sa.setSalePrice(item.getSalePrice());
                sa.setSaleAmount(item.getAmount());
                sa.setProduct(item.getProduct());
                sa.setSaleMan(bill.getInputUser());
                sa.setClient(bill.getClient());

                saleAccountMapper.insert(sa);

            }
            //更新订单状态
            stockOutcomeBillMapper.updateStatus(bill);
        }

    }
}

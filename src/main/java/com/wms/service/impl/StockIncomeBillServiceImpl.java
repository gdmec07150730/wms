package com.wms.service.impl;

import com.wms.domain.OrderBill;
import com.wms.domain.ProductStock;
import com.wms.domain.StockIncomeBill;
import com.wms.domain.StockIncomeBillItem;
import com.wms.mapper.ProductStockMapper;
import com.wms.mapper.StockIncomeBillItemMapper;
import com.wms.mapper.StockIncomeBillMapper;
import com.wms.page.PageResult;
import com.wms.query.QueryObject;
import com.wms.service.IStockIncomeBillService;
import com.wms.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

@Service
public class StockIncomeBillServiceImpl implements IStockIncomeBillService {

    @Autowired
    private StockIncomeBillMapper stockIncomeBillMapper;
    @Autowired
    private StockIncomeBillItemMapper stockIncomeBillItemMapper;

    @Autowired
    private ProductStockMapper productStockMapper;

    public void deleteByPrimaryKey(Long id) {
        //删除订单对象
        stockIncomeBillMapper.deleteByPrimaryKey(id);
        //删除订单对应的明细对象
        stockIncomeBillItemMapper.deleteBillId(id);
    }

    public void insert(StockIncomeBill record) {
        //1.设置录入人和录入时间
        record.setInputUser(UserContext.getCurrentUser());
        record.setInputTime(new Date());
        //设置审核状态
        record.setStatus(StockIncomeBill.STATUS_NORMAL);
        //2.遍历明细列表,计算总金额与总数量
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalNumber = BigDecimal.ZERO;

        for (StockIncomeBillItem item : record.getItems()) {
            BigDecimal amount = item.getCostPrice().multiply(item.getNumber()).setScale(2,BigDecimal.ROUND_HALF_UP);
            //3.遍历时计算明细的金额小计
            item.setAmount(amount);

            totalAmount = totalAmount.add(item.getAmount());
            totalNumber = totalNumber.add(item.getNumber());
        }
        record.setTotalAmount(totalAmount);
        record.setTotalNumber(totalNumber);
        //4.保存采购订单
        stockIncomeBillMapper.insert(record);
        //5.保存明细
        for (StockIncomeBillItem item : record.getItems()) {
            item.setBillId(record.getId());
            stockIncomeBillItemMapper.insert(item);
        }
    }

    public StockIncomeBill selectByPrimaryKey(Long id) {
        return stockIncomeBillMapper.selectByPrimaryKey(id);
    }

    public List<StockIncomeBill> selectAll() {
        return stockIncomeBillMapper.selectAll();
    }

    public PageResult query(QueryObject qo) {

        int rows = stockIncomeBillMapper.queryForCount(qo);
        if (rows == 0) {
            return PageResult.EMPTY_PAGE;
        }
        List<StockIncomeBill> list = stockIncomeBillMapper.queryForList(qo);

        return new PageResult(qo.getCurrentPage(), qo.getPageSize(), rows, list);
    }

    public void updateByPrimaryKey(StockIncomeBill record) {

        StockIncomeBill bill = stockIncomeBillMapper.selectByPrimaryKey(record.getId());

        if (bill.getStatus() == StockIncomeBill.STATUS_NORMAL){
            //删除原有的明细
            stockIncomeBillItemMapper.deleteBillId(record.getId());
            //计算总金额和总数量,并设置到对象中
            BigDecimal totalAmount = BigDecimal.ZERO;
            BigDecimal totalNumber = BigDecimal.ZERO;
            //遍历时计算明细的金额小计
            for (StockIncomeBillItem item : record.getItems()) {
                BigDecimal amount = item.getCostPrice().multiply(item.getNumber()).setScale(2, RoundingMode.HALF_UP);
                item.setAmount(amount);
                //为明细对象设置订单id
                item.setBillId(record.getId());
                stockIncomeBillItemMapper.insert(item);

                totalAmount = totalAmount.add(amount);
                totalNumber = totalNumber.add(item.getNumber());
            }
            record.setTotalNumber(totalNumber);
            record.setTotalAmount(totalAmount);
            //更新采购订单
            stockIncomeBillMapper.updateByPrimaryKey(record);
        }

    }

    public void audit(Long id) {
        StockIncomeBill old = stockIncomeBillMapper.selectByPrimaryKey(id);

        if (old.getStatus() == OrderBill.STATUS_NORMAL) {

            //设置审核信息,审核人,审核时间,状态
            old.setAuditor(UserContext.getCurrentUser());
            old.setStatus(OrderBill.STATUS_AUDITED);
            old.setAuditTime(new Date());

            //修改库存

            for (StockIncomeBillItem item : old.getItems()) {

                //根据货品id+仓库ID 得到库存信息对象
                ProductStock ps = productStockMapper.selectByDeptIdAndProductId(old.getId(), item.getProduct().getId());

                //判断ps是否为空
                if (ps == null) {
                    //为空新增一条明细
                    ps = new ProductStock();
                    ps.setAmount(item.getAmount());
                    ps.setPrice(item.getCostPrice());
                    ps.setStoreNumber(item.getNumber());
                    ps.setDepot(old.getDepot());
                    ps.setProduct(item.getProduct());

                    //保存库存明细
                    productStockMapper.insert(ps);

                }else{
                    //更新库存信息
                    //原库存总金额+本次入库总金额
                    ps.setAmount(ps.getAmount().add(item.getAmount()));
                    //原库存总量+本次入库总量
                    ps.setStoreNumber(ps.getStoreNumber().add(item.getNumber()));
                    //(原库存价格小计 + 本次入库总金额) / (原库存总量+本次入库总量)
                    ps.setPrice(ps.getAmount().divide(ps.getStoreNumber(),2,BigDecimal.ROUND_HALF_UP));

                    productStockMapper.updateByPrimaryKey(ps);
                }

            }

            //修改订单状态
            stockIncomeBillMapper.updateStatus(old);
        }

    }
}

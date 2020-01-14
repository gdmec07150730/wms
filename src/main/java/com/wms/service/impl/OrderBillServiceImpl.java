package com.wms.service.impl;

import com.wms.domain.OrderBill;
import com.wms.domain.OrderBillItem;
import com.wms.mapper.OrderBillItemMapper;
import com.wms.mapper.OrderBillMapper;
import com.wms.page.PageResult;
import com.wms.query.QueryObject;
import com.wms.service.IOrderBillService;
import com.wms.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;

@Service
public class OrderBillServiceImpl implements IOrderBillService {

    @Autowired
    private OrderBillMapper orderBillMapper;
    @Autowired
    private OrderBillItemMapper orderBillItemMapper;

    public void deleteByPrimaryKey(Long id) {
        //删除订单对应的明细对象
        orderBillItemMapper.deleteBillId(id);
        //删除订单对象
        orderBillMapper.deleteByPrimaryKey(id);
    }

    public void insert(OrderBill record) {
        //1.设置录入人和录入时间
        record.setInputUser(UserContext.getCurrentUser());
        record.setInputTime(new Date());
        //设置审核状态
        record.setStatus(OrderBill.STATUS_NORMAL);
        //2.遍历明细列表,计算总金额与总数量
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal totalNumber = BigDecimal.ZERO;
            //3.遍历时计算明细的金额小计
        for (OrderBillItem item : record.getItems()) {
            BigDecimal amount = item.getCostPrice().multiply(item.getNumber()).setScale(2, RoundingMode.HALF_UP);
            item.setAmount(amount);
            totalNumber = totalNumber.add(item.getNumber());
            totalAmount = totalAmount.add(item.getAmount());
        }
        record.setTotalAmount(totalAmount);
        record.setTotalNumber(totalNumber);
        //4.保存采购订单
        orderBillMapper.insert(record);
        //5.保存明细
        for (OrderBillItem item : record.getItems()) {
            item.setBillId(record.getId());
            orderBillItemMapper.insert(item);
        }
    }

    public OrderBill selectByPrimaryKey(Long id) {
        return orderBillMapper.selectByPrimaryKey(id);
    }

    public List<OrderBill> selectAll() {
        return orderBillMapper.selectAll();
    }

    public PageResult query(QueryObject qo) {

        int rows = orderBillMapper.queryForCount(qo);
        if (rows == 0) {
            return PageResult.EMPTY_PAGE;
        }
        List<OrderBill> list = orderBillMapper.queryForList(qo);

        return new PageResult(qo.getCurrentPage(), qo.getPageSize(), rows, list);
    }

    public void updateByPrimaryKey(OrderBill record) {
        OrderBill old = orderBillMapper.selectByPrimaryKey(record.getId());

        if(old.getStatus() == OrderBill.STATUS_NORMAL){

            //删除原有的明细
            orderBillItemMapper.deleteBillId(record.getId());

            //计算总金额和总数量,并设置到对象中
            BigDecimal totalAmount = BigDecimal.ZERO;
            BigDecimal totalNumber = BigDecimal.ZERO;
            //遍历时计算明细的金额小计
            for (OrderBillItem item : record.getItems()) {
                BigDecimal amount = item.getCostPrice().multiply(item.getNumber()).setScale(2, RoundingMode.HALF_UP);
                item.setAmount(amount);
                //为明细对象设置订单id
                item.setBillId(record.getId());
                orderBillItemMapper.insert(item);

                totalAmount = totalAmount.add(amount);
                totalNumber = totalNumber.add(item.getNumber());
            }
            record.setTotalNumber(totalNumber);
            record.setTotalAmount(totalAmount);
        //更新采购订单
        orderBillMapper.updateByPrimaryKey(record);
        }

    }

    public void audit(Long id) {
        //获取原来的订单对象,判断状态是否为未审核
        OrderBill old = orderBillMapper.selectByPrimaryKey(id);

        if (old.getStatus() == OrderBill.STATUS_NORMAL) {

            //设置审核信息,审核人,审核时间,状态
            old.setAuditor(UserContext.getCurrentUser());
            old.setStatus(OrderBill.STATUS_AUDITED);
            old.setAuditTime(new Date());

            //修改订单状态
            orderBillMapper.updateStatus(old);
        }


    }
}

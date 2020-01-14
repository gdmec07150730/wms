package com.wms.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter@Setter
public class OrderBill extends BaseDomain {

    public static final int STATUS_NORMAL = 0;//未审核
    public static final int STATUS_AUDITED = 1;//已审核

    private String sn; //订单编号
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date vdate;  //业务时间
    private Integer status = STATUS_NORMAL; //审核状态
    private BigDecimal totalAmount; //总金额
    private BigDecimal totalNumber; //总数量
    private Date auditTime;  //审核时间
    private Date inputTime; //录入时间

    private Employee inputUser; //录入人
    private Employee auditor; //审核人
    private Supplier supplier; //供应商

    //订单明细
    private List<OrderBillItem> items = new ArrayList<>();

    public String getDisplayStatus(){
        return status == STATUS_NORMAL ? "未审核" : "已审核";
    }

}

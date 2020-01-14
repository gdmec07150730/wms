package com.wms.query;

import com.wms.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter@Getter
public class OrderBillQueryObject extends QueryObject {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    private Long supplierId = -1L;
    private Integer status = -1;

    //设置结束时间为当前的最后的一秒
    public void setEndDate(Date date){
        if (date != null) {
            this.endDate = DateUtil.getEndDate(date);
        }
    }
}

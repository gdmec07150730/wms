package com.wms.query;

import com.wms.util.DateUtil;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@Setter@Getter
public class OrderChartQueryObject extends QueryObject {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    private Long supplierId = -1L;
    private Long brandId = -1L;
    private String groupType = "e.name";
    private String keyword;
    public static final Map<String,Object> GROUP_BY_TYPES = new LinkedHashMap<>();

    public String getGroupType(){
        return this.groupType;
    }

    static{
        GROUP_BY_TYPES.put("e.name","订货人员");
        GROUP_BY_TYPES.put("p.name","货品名称");
        GROUP_BY_TYPES.put("s.name","供应商");
        GROUP_BY_TYPES.put("p.brandName","货品品牌");
        GROUP_BY_TYPES.put("DATE_FORMAT (bill.vdate ,'%Y-%m')","订货日期(月)");
        GROUP_BY_TYPES.put("DATE_FORMAT (bill.vdate ,'%Y-%m-%d')","订货日期(日)");
    }

    //设置结束时间为当前的最后的一秒
    public void setEndDate(Date date){
        if (date != null) {
            this.endDate = DateUtil.getEndDate(date);
        }
    }



}

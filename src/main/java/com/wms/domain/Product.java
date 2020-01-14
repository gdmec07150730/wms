package com.wms.domain;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Setter@Getter
public class Product extends BaseDomain {

    private String name;
    private String sn;
    private BigDecimal costPrice;
    private BigDecimal salePrice;
    private String imagePath;
    private String intro;
    private Long brandId;
    private String brandName;

    public String getSmallImagePath(){
        if(imagePath != null){
            int index = imagePath.lastIndexOf(".");
            return imagePath.substring(0,index)+"_small"+imagePath.substring(index);
        }
        return "";
    }

    public String getJsonString(){
        Map<String,Object> map = new HashMap<>();

        map.put("id",this.getId());
        map.put("name",this.getName());
        map.put("costPrice",this.getCostPrice());
        map.put("brandName",this.getBrandName());
        map.put("salePrice",this.getSalePrice());

        return JSON.toJSONString(map);

    }

}

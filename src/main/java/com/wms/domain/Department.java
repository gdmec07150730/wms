package com.wms.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter@ToString
public class Department extends BaseDomain {
    private Long id;

    private String name;
    private String sn;


}
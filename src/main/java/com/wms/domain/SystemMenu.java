package com.wms.domain;

import lombok.Getter;
import lombok.Setter;

@Setter@Getter
public class SystemMenu extends BaseDomain {

    private String name;//当前菜单名称
    private String url;
    private String sn; //

    private SystemMenu parent;
}

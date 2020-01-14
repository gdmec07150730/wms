package com.wms.util;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter@Getter
public class JsonData {

    private int total;
    private List<?> rows;

}

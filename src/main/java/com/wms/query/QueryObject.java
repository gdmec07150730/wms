package com.wms.query;

import lombok.Getter;
import lombok.Setter;

@Setter@Getter
public class QueryObject {

    private int currentPage = 1;
    private int pageSize = 5;
    private int page;
    private int rows;


    public void setPage(int page){
        this.page = page;
        currentPage = page;
    }

    public void  setRows(int rows){
        this.rows = rows;
        pageSize = rows;
    }

    public int getStart(){
        return (currentPage - 1) * pageSize;
    }
}

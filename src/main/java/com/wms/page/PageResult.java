package com.wms.page;

import lombok.Getter;
import lombok.Setter;

import java.util.Collections;
import java.util.List;

@Setter@Getter
public class PageResult {

    private int currentPage;
    private int pageSize;

    private int totalCount;
    private List<?> datas;

    private int totalPage;
    private int prevPage;
    private int nextPage;

    public static final PageResult EMPTY_PAGE = new PageResult(1,1,0, Collections.EMPTY_LIST);

    public PageResult(int currentPage, int pageSize, int totalCount, List<?> datas) {

        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.datas = datas;

        this.totalPage = totalCount % pageSize == 0 ?totalCount / pageSize : totalCount / pageSize +1;
        this.prevPage =  currentPage - 1 > 1 ? currentPage - 1 : 1;
        this.nextPage = currentPage + 1 < totalPage ? currentPage + 1 : totalPage;

    }



}

package com.locngo.loansystem.util.pagingandsorting;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public class PageRequestBuilder {

    private static final int DEFAULT_PAGE_NUM = 1;

    private static final int DEFAULT_PAGE_SIZE = 10;

    private int pageNum = DEFAULT_PAGE_NUM;

    private int pageSize = DEFAULT_PAGE_SIZE;

    public PageRequestBuilder hasPageNum(int pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public PageRequestBuilder hasPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public PageRequest build() {
        return new PageRequest(this.pageNum, this.pageSize);
    }
}

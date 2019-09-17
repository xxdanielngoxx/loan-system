package com.locngo.loansystem.util.pagingandsorting;

import java.util.List;

public class ResponseData <T>{

    private int pageNum;

    private int pageSize;

    private int totalElements;

    private int totalPages;

    private List<T> elements;
}

package com.nlo.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ApiResponse {
    public String status;
    public String message;
    private Number total;
    private Object data;
    private Number totalPages;
    private Number pageNum;
    private Number pageSize;
    private Number errorCode;
    private Boolean isSuccess;
    private Number currentPageSize;
}
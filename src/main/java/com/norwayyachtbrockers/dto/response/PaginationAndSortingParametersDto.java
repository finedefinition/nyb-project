package com.norwayyachtbrockers.dto.response;

import lombok.Data;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

@Data
public class PaginationAndSortingParametersDto {

//    @Min(value = 1, message = "Page number must be greater than or equal to 1")
    private int page = 1; // По умолчанию первая страница

    private String sortBy = "id"; // По умолчанию сортировка по 'id'

//    @Pattern(regexp = "asc|desc", flags = Pattern.Flag.CASE_INSENSITIVE, message = "orderBy must be 'asc' or 'desc'")
    private String orderBy = "asc"; // По умолчанию по возрастанию ('asc')

    public void setOrderBy(String orderBy) {
        if ("descend".equalsIgnoreCase(orderBy)) {
            this.orderBy = "desc";
        } else if ("ascend".equalsIgnoreCase(orderBy)) {
            this.orderBy = "asc";
        } else {
            this.orderBy = orderBy.toLowerCase();
        }
    }

    public void setPage(int page) {
        this.page = Math.max(page, 1);
    }
}

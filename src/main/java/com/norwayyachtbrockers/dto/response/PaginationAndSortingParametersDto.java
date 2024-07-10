package com.norwayyachtbrockers.dto.response;

import lombok.Data;

@Data
public class PaginationAndSortingParametersDto {
    private int page = 1; // default to the first page
    private String sortBy = "id"; // default to sorting by id
    private String orderBy = "ascend"; // default to ascending order
}

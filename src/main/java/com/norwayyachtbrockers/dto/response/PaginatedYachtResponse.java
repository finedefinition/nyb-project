package com.norwayyachtbrockers.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class PaginatedYachtResponse {
    private int currentPage;
    private int totalPages;
    private long totalItems;
    private List<YachtShortResponseDto> yachts;
}

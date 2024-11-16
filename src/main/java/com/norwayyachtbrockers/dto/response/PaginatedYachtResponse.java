package com.norwayyachtbrockers.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class PaginatedYachtResponse {
    private Pagination pagination;
    private List<YachtShortResponseDto> yachts;

    @Data
    public static class Pagination {
        private int currentPage;
        private int totalPages;
//        private long totalItems;
    }
}

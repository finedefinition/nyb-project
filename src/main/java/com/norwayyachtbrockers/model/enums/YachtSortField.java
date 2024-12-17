package com.norwayyachtbrockers.model.enums;

public enum YachtSortField {
    ID("id"),
    FAVOURITES_COUNT("favouritesCount"),
    PRICE("price"),
    YACHT_CREATED_AT("yachtModel.year");

    private final String fieldName;

    YachtSortField(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public static YachtSortField fromSortBy(String sortBy) {
        switch (sortBy) {
            case "yacht_favourites_count":
                return FAVOURITES_COUNT;
            case "yacht_price":
                return PRICE;
            case "yacht_created_at":
                return YACHT_CREATED_AT;
            case "id":
                return ID;
            default:
                throw new IllegalArgumentException("Unsupported sort field: " + sortBy);
        }
    }
}


package com.norwayyachtbrockers.dto.mapper;

import java.util.HashMap;
import java.util.Map;

public class FieldMapper {
    private static final Map<String, String> fieldMap = new HashMap<>();

    static {
        fieldMap.put("yacht_id", "id");
        fieldMap.put("yacht_main_image_key", "mainImageKey");
        fieldMap.put("yacht_make", "yachtModel.make");
        fieldMap.put("yacht_model", "yachtModel.model");
        fieldMap.put("yacht_country", "country.name");
        fieldMap.put("yacht_town", "town.name");
        fieldMap.put("yacht_owner_first_name", "ownerInfo.firstName");
        fieldMap.put("yacht_owner_last_name", "ownerInfo.lastName");
        fieldMap.put("yacht_owner_telephone", "ownerInfo.telephone");
        fieldMap.put("yacht_owner_email", "ownerInfo.email");
        fieldMap.put("yacht_created_at", "createdAt");
    }

    public static String getEntityField(String dtoField) {
        return fieldMap.getOrDefault(dtoField, "id"); // Default to sorting by id
    }
}


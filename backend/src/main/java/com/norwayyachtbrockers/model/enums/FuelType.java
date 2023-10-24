package com.norwayyachtbrockers.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum FuelType {
    ALL_FUEL_TYPES,
    DIESEL,
    ELECTRIC,
    GAS_LPG,
    PETROL_GASOLINE,
    HYBRID;

    @JsonValue
    @Override
    public String toString() {
        return switch (this) {
            case ALL_FUEL_TYPES -> "All fuel types";
            case DIESEL -> "Diesel";
            case ELECTRIC -> "Electric";
            case GAS_LPG -> "Gas/LPG";
            case PETROL_GASOLINE -> "Petrol/Gasoline";
            case HYBRID -> "Hybrid";
            default -> throw new IllegalArgumentException("This fuel type is not supported");
        };
    }
}


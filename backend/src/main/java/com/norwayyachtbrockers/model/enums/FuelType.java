package com.norwayyachtbrockers.model.enums;

public enum FuelType {
    ALL_FUEL_TYPES,
    DIESEL,
    ELECTRIC,
    GAS_LPG,
    PETROL_GASOLINE,
    STEAM;

    @Override
    public String toString() {
        return switch (this) {
            case ALL_FUEL_TYPES -> "All fuel types";
            case DIESEL -> "Diesel";
            case ELECTRIC -> "Electric";
            case GAS_LPG -> "Gas/LPG";
            case PETROL_GASOLINE -> "Petrol/Gasoline";
            default -> throw new IllegalArgumentException("This fuel type is not supported");
        };
    }
}


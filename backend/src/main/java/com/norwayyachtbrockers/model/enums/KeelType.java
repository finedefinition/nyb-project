package com.norwayyachtbrockers.model.enums;

import com.fasterxml.jackson.annotation.JsonValue;

public enum KeelType {
    ALL_KEEL_TYPES,
    BILGE_KEEL,
    BULB_KEEL,
    CANTING_KEEL,
    CENTREBOARD,
    FIN_KEEL,
    FULL_KEEL,
    LIFTING_KEEL,
    LONG_KEEL,
    SHOAL_KEEL,
    WINGED_KEEL;

    @JsonValue
    @Override
    public String toString() {
        return switch (this) {
            case ALL_KEEL_TYPES -> "All keel types";
            case BILGE_KEEL -> "Bilge & Twin Keel";
            case BULB_KEEL -> "Bulb Keel";
            case CANTING_KEEL -> "Canting Keel";
            case CENTREBOARD -> "Centreboard & Daggerboard";
            case FIN_KEEL -> "Fin Keel";
            case FULL_KEEL -> "Full Keel";
            case LIFTING_KEEL -> "Lifting & Swing Keel";
            case LONG_KEEL -> "Long Keel";
            case SHOAL_KEEL -> "Shoal Keel";
            case WINGED_KEEL -> "Winged Keel";
            default -> throw new IllegalArgumentException("This keel type is not supported");
        };
    }
}
package com.norwayyachtbrockers.model.enums;

import jakarta.persistence.Enumerated;

public enum UserRoles {
    ROLE_USER,
    ROLE_ADMIN,
    ROLE_MANAGER;

    public static UserRoles fromString(String roleStr) {
        for (UserRoles role : UserRoles.values()) {
            if (role.name().equalsIgnoreCase(roleStr)) {
                return role;
            }
        }
        throw new IllegalArgumentException("No constant with text " + roleStr + " found");
    }
}

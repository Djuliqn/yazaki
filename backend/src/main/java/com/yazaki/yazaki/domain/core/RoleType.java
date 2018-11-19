package com.yazaki.yazaki.domain.core;

public enum RoleType {

    ROLE_ADMIN("ROLE_ADMIN"),
    ROLE_EMPLOYEE("ROLE_EMPLOYEE"),
    ROLE_USER("ROLE_USER");

    private String type;

    RoleType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}

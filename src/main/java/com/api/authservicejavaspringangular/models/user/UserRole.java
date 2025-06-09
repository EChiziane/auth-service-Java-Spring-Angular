package com.api.authservicejavaspringangular.models.user;

public enum UserRole {
    ADMIN("admin"),
    USER("com/api/authservicejavaspringangular/models/user");

    private final String role;

    UserRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
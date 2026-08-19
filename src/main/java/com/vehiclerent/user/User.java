package com.vehiclerent.user;

/**
 * Represents a system user with role-based access (admin vs customer).
 */
public class User {

    public enum Role {
        ADMIN, CUSTOMER
    }

    private final String id;
    private final String username;
    private final String password;
    private final Role role;

    public User(String id, String username, String password, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public boolean isAdmin() {
        return role == Role.ADMIN;
    }
}

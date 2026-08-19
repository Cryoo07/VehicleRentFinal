package com.vehiclerent.security;

import com.vehiclerent.user.User;

/**
 * Proxy Pattern (support): Thread-local security context storing current authenticated user.
 */
public final class SecurityContext {

    private static final ThreadLocal<User> currentUser = new ThreadLocal<>();

    private SecurityContext() {
    }

    public static void setCurrentUser(User user) {
        currentUser.set(user);
    }

    public static User getCurrentUser() {
        return currentUser.get();
    }

    public static void clear() {
        currentUser.remove();
    }
}

package com.example.backend.security;

public class AuthContext {

    private static final ThreadLocal<AuthenticatedUser> currentUser = new ThreadLocal<>();

    public static void setCurrentUser(AuthenticatedUser user) {
        currentUser.set(user);
    }

    public static AuthenticatedUser getCurrentUser() {
        return currentUser.get();
    }

    public static void clear() {
        currentUser.remove();
    }
}

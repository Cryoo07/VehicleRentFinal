package com.vehiclerent.repository;

import com.vehiclerent.user.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * In-memory user storage for unit tests and offline mode.
 */
public class InMemoryUserRepository implements UserRepository {

    private final Map<String, User> usersByUsername = new HashMap<>();

    @Override
    public void save(User user) {
        usersByUsername.put(user.getUsername(), user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(usersByUsername.get(username));
    }

    @Override
    public boolean existsByUsername(String username) {
        return usersByUsername.containsKey(username);
    }
}

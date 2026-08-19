package com.vehiclerent.repository;

import com.vehiclerent.user.User;

import java.util.Optional;

public interface UserRepository {

    void save(User user);

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);
}

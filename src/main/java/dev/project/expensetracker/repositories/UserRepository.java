package dev.project.expensetracker.repositories;

import dev.project.expensetracker.entities.User;

import java.util.Optional;

public interface UserRepository {
    User addUser(User user);

    Optional<User> getUserByEmail(String email);
}


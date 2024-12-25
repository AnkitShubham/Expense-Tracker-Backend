package dev.project.expensetracker.services;

import dev.project.expensetracker.dao.UserDao;
import dev.project.expensetracker.entities.User;
import dev.project.expensetracker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServices implements UserRepository {

    @Autowired
    private UserDao userDao;

    public User addUser(User user) {
        userDao.save(user);
        return user;
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userDao.findByEmail(email);
    }
}

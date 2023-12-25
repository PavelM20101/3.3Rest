package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;

public interface UserDAO {
    User getUserByUsername(String username);

    User getUserByEmail(String email);

    void deleteUser(int id);

    User getUser(int id);

    void updateUser(User user);

    void createUser(User user);

    List<User> getAllUsers();
}

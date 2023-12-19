package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;

public interface UserService {
    User getUserByEmail(String email);
    void addUser(User user);
    User getUserById(int id);
    void updateUser(User user);
    void removeUserById(int id);
    List<User> listUsers();
}

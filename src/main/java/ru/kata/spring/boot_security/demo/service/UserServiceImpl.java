package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDAO;
import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    private final UserDAO userDao;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserDAO userDao, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userDao = userDao;
    }
    //Transactional(readOnly = true)
    @Transactional(readOnly = true)
    public User getUserByEmail(String email) {

        return userDao.getUserByEmail(email);
    }
    @Override
    public void addUser(User user) {
        userDao.addUser(user);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
    }
    @Transactional(readOnly = true)
    public User getUserById(int id) {

        return userDao.getUserById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public void updateUser(User user) {

        userDao.updateUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public void removeUserById(int id) {

        userDao.removeUserById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> listUsers() {

        return userDao.listUsers();
    }

    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userDao.getUserByEmail(email);
    }
}

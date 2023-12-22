package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDAO;
import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
    @Autowired
    private UserDAO userDao;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional(readOnly = true)
    public User getUserByUsername(String username) {
        return userDao.getUserByUsername(username);
    }

    //    @Autowired
//    public UserServiceImpl(UserDAO userDao, BCryptPasswordEncoder bCryptPasswordEncoder) {
//        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
//        this.userDao = userDao;
//    }
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
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDao.getUserByUsername(username);
    }
}

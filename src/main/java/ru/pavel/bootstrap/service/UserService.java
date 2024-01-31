package ru.pavel.bootstrap.service;

import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import ru.pavel.bootstrap.model.User;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface UserService extends UserDetailsService {
    void deleteUser(Long userId);
    User updateUser(User user, BindingResult bindingResult);
    User insertUser(User user, BindingResult bindingResult);
    User findUser(Long userId);
    List<User> findAllUsers();
    UserDetails loadUserByUsername(String email);
    String getPage(Model model, HttpSession session, @Nullable Authentication auth);

    }

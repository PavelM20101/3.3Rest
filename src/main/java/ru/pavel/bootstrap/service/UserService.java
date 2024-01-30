package ru.pavel.bootstrap.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.pavel.bootstrap.config.exception.LoginException;
import ru.pavel.bootstrap.model.User;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface UserService extends UserDetailsService {
    void deleteUser(Long userId);
    void updateUser(User user, BindingResult bindingResult, RedirectAttributes redirectAttributes);
    void insertUser(User user, BindingResult bindingResult, RedirectAttributes redirectAttributes);
    User findUser(Long userId);
    List<User> findAllUsers();
    UserDetails loadUserByUsername(String email);
    void authenticateOrLogout(Model model, HttpSession session, LoginException authenticationException, String authenticationName);
}

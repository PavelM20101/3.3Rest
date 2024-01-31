package ru.pavel.bootstrap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import ru.pavel.bootstrap.model.User;
import ru.pavel.bootstrap.repository.UserRepository;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;
    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }



    @Override
    @Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    @Transactional
    public User updateUser(User user, BindingResult bindingResult) {
        bindingResult = checkBindingResultForPasswordField(bindingResult);
        if (!bindingResult.hasErrors()) {
            user.setPassword(user.getPassword().isEmpty() ? findUser(user.getId()).getPassword() : passwordEncoder.encode(user.getPassword()));
            try {
                userRepository.save(user);
            } catch (DataIntegrityViolationException e){
                String oldPassword = user.getPassword();
                user.setPassword(oldPassword);
            }
        } else {
            try {
                throw new Exception();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return user;
    }
    @Override
    @Transactional
    public User insertUser(User user, BindingResult bindingResult) {
        if (!bindingResult.hasErrors()){
            try {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                user = userRepository.save(user);
            } catch (DataIntegrityViolationException e){
                String oldPassword = user.getPassword();
                user.setPassword(oldPassword);
                throw e;
            }

        }
        return user;
    }

    @Override
    public User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() ->
                new IllegalArgumentException(String.format("User with ID %d not found", userId)));
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "firstName", "lastName"));
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Username %s not found", email))
        );
    }

    private BindingResult checkBindingResultForPasswordField(BindingResult bindingResult) {
        if (!bindingResult.hasFieldErrors()) {
            return bindingResult;
        }

        User user = (User) bindingResult.getTarget();
        BindingResult newBindingResult = new BeanPropertyBindingResult(user, bindingResult.getObjectName());
        for (FieldError error : bindingResult.getFieldErrors()) {
            if (!user.isNew() && !error.getField().equals("password")) {
                newBindingResult.addError(error);
            }
        }

        return newBindingResult;
    }

    @Override
    public String getPage(Model model, HttpSession session, @Nullable Authentication auth) {
        if (Objects.isNull(auth)) {
            model.addAttribute("authenticatedName", session.getAttribute("authenticatedName"));
            session.removeAttribute("authenticatedName");

            model.addAttribute("authenticationException", session.getAttribute("authenticationException"));
            session.removeAttribute("authenticationException");

            return "login-page";
        }

        User user = (User) auth.getPrincipal();
        model.addAttribute("user", user);

        if (user.hasRole("ROLE_ADMIN")) {
            return "main-page";
        }

        if (user.hasRole("ROLE_USER")) {
            return "user-page";
        }

        return "access-denied-page";

    }
}

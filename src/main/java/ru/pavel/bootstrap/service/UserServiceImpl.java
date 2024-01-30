package ru.pavel.bootstrap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.pavel.bootstrap.config.exception.LoginException;
import ru.pavel.bootstrap.model.User;
import ru.pavel.bootstrap.repository.UserRepository;

import javax.servlet.http.HttpSession;
import java.util.List;

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
    public void updateUser(User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        bindingResult = checkBindingResultForPasswordField(bindingResult);
        if (!bindingResult.hasErrors()) {
            String oldPassword = user.getPassword();
            try {
                user.setPassword(user.getPassword().isEmpty() ? // todo если нет такого юзера try
                        findUser(user.getId()).getPassword() :
                        passwordEncoder.encode(user.getPassword()));
                userRepository.save(user);
            } catch (DataIntegrityViolationException e) {
                user.setPassword(oldPassword);
                addErrorIfDataIntegrityViolationException(bindingResult);
                addRedirectAttributesIfErrorsExists(user, bindingResult, redirectAttributes);
            }
        } else {
            addRedirectAttributesIfErrorsExists(user, bindingResult, redirectAttributes);
        }
    }

    @Override
    @Transactional
    public void insertUser(User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (!bindingResult.hasErrors()) {
            String oldPassword = user.getPassword();
            try {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                userRepository.save(user);
            } catch (DataIntegrityViolationException e) {
                user.setPassword(oldPassword);
                addErrorIfDataIntegrityViolationException(bindingResult);
                addRedirectAttributesIfErrorsExists(user, bindingResult, redirectAttributes);
            }
        } else {
            addRedirectAttributesIfErrorsExists(user, bindingResult, redirectAttributes);
        }
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
    private void addErrorIfDataIntegrityViolationException(BindingResult bindingResult) {
        bindingResult.addError(new FieldError(bindingResult.getObjectName(),
                "email", "E-mail must be unique"));
    }
    private void addRedirectAttributesIfErrorsExists(User user, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("user", user);
        redirectAttributes.addFlashAttribute("bindingResult", bindingResult);
    }
    public void authenticateOrLogout(Model model, HttpSession session, LoginException authenticationException, String authenticationName) {
        if (authenticationException != null) {
            try {
                model.addAttribute("authenticationException", authenticationException);
                session.removeAttribute("Authentication-Exception");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            model.addAttribute("authenticationException", new LoginException(null));
        }

        if (authenticationName != null) {
            try {
                model.addAttribute("authenticationName", authenticationName);
                session.removeAttribute("Authentication-Name");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

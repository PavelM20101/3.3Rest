package ru.pavel.bootstrap.service;

import org.springframework.ui.Model;
import ru.pavel.bootstrap.config.exception.LoginException;
import ru.pavel.bootstrap.model.Role;

import javax.servlet.http.HttpSession;

public interface RoleService{
    Iterable<Role> findAllRoles();
}

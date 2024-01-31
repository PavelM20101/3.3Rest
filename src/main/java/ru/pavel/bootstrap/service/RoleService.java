package ru.pavel.bootstrap.service;

import ru.pavel.bootstrap.model.Role;

import javax.servlet.http.HttpSession;

public interface RoleService{
    Iterable<Role> findAllRoles();
}

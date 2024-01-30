package ru.pavel.bootstrap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import ru.pavel.bootstrap.config.exception.LoginException;
import ru.pavel.bootstrap.model.Role;
import ru.pavel.bootstrap.repository.RoleRepository;

import javax.servlet.http.HttpSession;

@Service
public class RoleServiceImpl implements RoleService{
    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Iterable<Role> findAllRoles() {
        return roleRepository.findAll();
    }
}

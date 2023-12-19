package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.RoleDAO;
import ru.kata.spring.boot_security.demo.entity.Role;

import java.util.Set;
@Service
public class RoleServiceImpl implements RoleService{
    private final RoleDAO roleDao;

    @Autowired
    public RoleServiceImpl(RoleDAO roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Role> getAllRoles() {

        return roleDao.getAllRoles();
    }

    @Override
    @Transactional(readOnly = true)
    public Role getRoleByName(String name) {

        return roleDao.getRoleByName(name);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Role> getSetOfRoles(String[] roleNames) {
        return
                roleDao.getSetOfRoles(roleNames);
    }

    @Override
    @Transactional(readOnly = true)
    public void add(Role role) {

        roleDao.add(role);
    }

    @Override
    @Transactional(readOnly = true)
    public void edit(Role role) {

        roleDao.edit(role);
    }
    //изменён на обёртку в связи с последнем замечанием в 2.3.1
    @Override
    @Transactional(readOnly = true)
    public Role getById(int id) {

        return roleDao.getById(id);
    }
}

package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.entity.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class RoleDAOImpl implements RoleDAO{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Set<Role> getAllRoles() {
        List<Role> roleList = entityManager.createQuery("from Role", Role.class).getResultList();
        return new HashSet<>(roleList);
    }

    @Override
    public Role getRoleByName(String role) {
        return entityManager.createQuery(
                "from Role where name=:role", Role.class
        ).setParameter("role", role).getSingleResult();
    }

    @Override
    public Set<Role> getSetOfRoles(String[] roleNames) {
        Set<Role> roleSet = new HashSet<>();
        for (String roleName : roleNames) {
            Role role = getRoleByName(roleName);
            if (role != null) {
                roleSet.add(role);
            }
        }
        return roleSet;
    }

    @Override
    public void add(Role role) {

        entityManager.persist(role);
    }

    @Override
    public void edit(Role role) {

        entityManager.merge(role);
    }

    @Override
    public Role getById(int id) {

        return entityManager.find(Role.class, id);
    }
}

package ru.kata.spring.boot_security.demo.dao;

import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.entity.Role;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
public class RoleDAOImpl implements RoleDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Set<Role> getAllRoles() {
        List<Role> roles = entityManager.createQuery("from Role", Role.class).getResultList();
        return new HashSet<>(roles);
    }

    @Override
    public Role getRoleByName(String role) {
        return entityManager.createQuery(
                "SELECT r from Role r where r.name=:role", Role.class
        ).setParameter("role", role).getSingleResult();
    }
}

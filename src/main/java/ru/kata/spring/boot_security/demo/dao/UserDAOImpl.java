package ru.kata.spring.boot_security.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {
    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public UserDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public User getUserByUsername(String username) {
        return entityManager.createQuery("from User where username =:username", User.class).setParameter("username", username).getSingleResult();
    }

    @Override
    public User getUserByEmail(String email) {
        return entityManager.createQuery(
                        "FROM User WHERE email =:email", User.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    @Override
    public void deleteUser(int id) {
        User user = this.getUser(id);
        if (user == null) {
            throw new NullPointerException();
        }
        entityManager.remove(user);
        entityManager.flush();
    }

    @Override
    public User getUser(int id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
        entityManager.flush();
    }

    @Override
    public void createUser(User user) {
        entityManager.persist(user);
        entityManager.flush();
    }

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }
}

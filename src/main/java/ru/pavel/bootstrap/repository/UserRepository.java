package ru.pavel.bootstrap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import ru.pavel.bootstrap.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //Использует методы JpaRepository
    Optional<UserDetails> findByEmail(String email);
}

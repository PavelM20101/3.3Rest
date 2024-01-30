package ru.pavel.bootstrap.repository;

import org.springframework.data.repository.CrudRepository;
import ru.pavel.bootstrap.model.Role;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    // Использует методы CrudRepository
}

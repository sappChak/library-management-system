package org.example.bookstore.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.example.bookstore.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String roleName);

    List<Role> findAllByNameIn(Set<String> roleNames);
}

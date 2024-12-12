package org.example.bookstore.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.example.bookstore.entity.Role;
import org.example.bookstore.entity.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole roleName);

    List<Role> findAllByNameIn(Set<ERole> roleNames);
}

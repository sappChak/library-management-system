package org.example.bookstore.service;

import java.util.List;
import java.util.Set;

import org.example.bookstore.entity.Role;
import org.example.bookstore.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getRoleByName(String roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found with name: " + roleName));
    }

    public List<Role> getRolesByIds(Set<Long> roleIds) {
        return roleRepository.findAllById(roleIds);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public List<Role> getRolesByName(Set<String> roleNames) {
        return roleRepository.findAllByNameIn(roleNames);
    }
}

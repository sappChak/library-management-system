package org.example.library.service;

import java.util.List;
import java.util.Set;

import org.example.library.entity.Role;
import org.example.library.entity.enums.ERole;
import org.example.library.repository.RoleRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role getRoleByName(ERole roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found with name: " + roleName));
    }

    public List<Role> getRolesByIds(Set<Long> roleIds) {
        return roleRepository.findAllById(roleIds);
    }

    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    public List<Role> getRolesByName(Set<ERole> roleNames) {
        return roleRepository.findAllByNameIn(roleNames);
    }
}

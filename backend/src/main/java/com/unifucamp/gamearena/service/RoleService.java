package com.unifucamp.gamearena.service;

import com.unifucamp.gamearena.entity.Role;
import com.unifucamp.gamearena.enums.Roles;
import com.unifucamp.gamearena.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findRole(Roles roleName) {
        return roleRepository.findByName(roleName)
                .orElseGet(() -> {
                    Role role = new Role();
                    role.setName(roleName);
                    return roleRepository.save(role);
                });    }

    public List<Role> findRoles(Roles... roleNames) {
        return Arrays.stream(roleNames)
                .map(this::findRole)
                .toList();
    }
}

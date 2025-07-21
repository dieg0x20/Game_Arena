package com.unifucamp.gamearena.service;

import com.unifucamp.gamearena.entity.Role;
import com.unifucamp.gamearena.enums.RoleName;
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

    public Role buscarRole(RoleName roleName) {
        return roleRepository.findByName(roleName)
                .orElseGet(() -> roleRepository.save(
                        Role.builder().name(roleName).build()
                ));
    }

    public List<Role> buscarRoles(RoleName... roleNames) {
        return Arrays.stream(roleNames)
                .map(this::buscarRole)
                .toList();
    }
}

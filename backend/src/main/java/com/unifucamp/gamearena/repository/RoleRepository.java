package com.unifucamp.gamearena.repository;

import com.unifucamp.gamearena.entity.Role;
import com.unifucamp.gamearena.enums.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByName(Roles name);
}

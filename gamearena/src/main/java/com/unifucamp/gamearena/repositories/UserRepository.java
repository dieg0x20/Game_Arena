package com.unifucamp.gamearena.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unifucamp.gamearena.domain.User;

public interface UserRepository extends JpaRepository<User, String> {
  Optional<User> findByEmail(String email);
}

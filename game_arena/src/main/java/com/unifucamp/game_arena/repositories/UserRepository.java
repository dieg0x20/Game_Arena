package com.unifucamp.game_arena.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unifucamp.game_arena.entity.User;

public interface UserRepository extends JpaRepository<User, String>{
  Optional<User> findByEmail(String email);
}

package com.unifucamp.game_arena.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.unifucamp.game_arena.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {}

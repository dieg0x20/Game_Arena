package com.unifucamp.game_arena.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.unifucamp.game_arena.entity.User;
import com.unifucamp.game_arena.repositories.UserRepository;

@Service
public class UserService {
  
  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Page<User> findAll(int page, int items){
    Pageable pageable = PageRequest.of(page, items);
    return userRepository.findAll(pageable);
  }

}

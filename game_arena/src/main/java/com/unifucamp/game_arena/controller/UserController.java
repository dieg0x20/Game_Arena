package com.unifucamp.game_arena.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unifucamp.game_arena.entity.User;
import com.unifucamp.game_arena.service.UserService;

@RestController
public class UserController {

  private final UserService userService;
  public UserController(UserService userService) {
    this.userService = userService;
  }
  @GetMapping
  public ResponseEntity<Page<User>> findAll(@RequestParam int page, @RequestParam int items){
    return ResponseEntity.ok(userService.findAll(page, items));
  }
  
}

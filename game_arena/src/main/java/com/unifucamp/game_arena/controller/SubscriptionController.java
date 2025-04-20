package com.unifucamp.game_arena.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.unifucamp.game_arena.entity.Subscription;
import com.unifucamp.game_arena.service.SubscriptionService;

@RestController
public class SubscriptionController {

  private final SubscriptionService subscriptionService;

  public SubscriptionController(SubscriptionService subscriptionService) {
    this.subscriptionService = subscriptionService;
  }

  @GetMapping("/")
  public ResponseEntity<Page<Subscription>> findAll(@RequestParam int page, @RequestParam int size) {
    return ResponseEntity.ok(subscriptionService.findAll(page, size));
  }

}

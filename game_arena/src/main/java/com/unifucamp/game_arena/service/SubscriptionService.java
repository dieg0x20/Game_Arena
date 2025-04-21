package com.unifucamp.game_arena.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.unifucamp.game_arena.entity.Subscription;
import com.unifucamp.game_arena.repositories.SubscriptionRepository;

@Service
public class SubscriptionService {

  private final SubscriptionRepository subscriptionRepository;

  public SubscriptionService(SubscriptionRepository subscriptionRepository) {
    this.subscriptionRepository = subscriptionRepository;
  }

  public Page<Subscription> findAll(Pageable pageable) {

    try {

      return subscriptionRepository.findAll(pageable);

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}

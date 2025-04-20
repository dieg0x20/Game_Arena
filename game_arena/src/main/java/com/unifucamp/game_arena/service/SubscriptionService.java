package com.unifucamp.game_arena.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

  public Page<Subscription> findAll(int page, int size) {

    if (page < 0)
      page = 0;

    if (size < 5)
      size = 5;

    if (size > 30)
      size = 30;

    Pageable pageable = PageRequest.of(page, size);

    try {

      return subscriptionRepository.findAll(pageable);

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}

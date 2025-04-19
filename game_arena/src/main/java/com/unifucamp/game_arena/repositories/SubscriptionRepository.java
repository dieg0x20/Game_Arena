package com.unifucamp.game_arena.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import com.unifucamp.game_arena.entity.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {}

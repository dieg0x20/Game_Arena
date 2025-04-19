package com.unifucamp.game_arena.entity;

import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Table(name = "inscricoes")
@Data
@Entity
public class Subscription {

  @Setter(AccessLevel.NONE)
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  @Column(name = "nome_completo")
  private String username;

  @Column(name = "nickname")
  private String nickname;

  @Column(name = "email")
  private String email;

  @Column(name = "data_nascimento")
  private LocalDateTime birthDate;

  @Column(name = "aceitou_termos")
  private boolean aceptTerms;

  @Column (name = "comprovante_pagamento")
  private String recipientUrl;

  @Setter(AccessLevel.NONE)
  @CreatedDate
  @Column(name = "data_inscricao")
  private LocalDateTime subscribedAt;

  @Column(name = "ativo")
  private boolean isActive;

  @Column(name = "pagamento")
  private boolean isPaid;

}

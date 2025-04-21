package com.unifucamp.gamearena.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "isncricoes")
public class Inscricao {

  @Id
  private Long id;

  private String nomeCompleto;

  private String nickname;

  private String email;

  @Column(name = "data_nascimento")
  private LocalDate dataNascimento;

  @Column(name = "comprovante_pagamento")
  private String comprovantePagamento;

  @Column(name = "aceitou_termos")
  private boolean aceitouTermos;

  @Column(name = "data_inscricao")
  private LocalDateTime dataInscricao;

  private boolean ativo;

  private boolean pagamento;
}

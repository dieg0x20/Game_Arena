package com.unifucamp.gamearena.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.unifucamp.gamearena.domain.Inscricao;

public interface InscricaoRepository extends JpaRepository<Inscricao, Long> {
}

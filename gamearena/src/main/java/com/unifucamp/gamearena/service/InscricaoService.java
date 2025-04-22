package com.unifucamp.gamearena.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.unifucamp.gamearena.dto.InscricaoResponse;
import com.unifucamp.gamearena.repositories.InscricaoRepository;

@Service
public class InscricaoService {

  private final InscricaoRepository inscricaoRepository;

  public InscricaoService(InscricaoRepository inscricaoRepository) {
    this.inscricaoRepository = inscricaoRepository;
  }

  public Page<InscricaoResponse> findAll(Pageable pageable) {
    try {
      return inscricaoRepository.findAll(pageable).map(i -> new InscricaoResponse(
          i.getId(),
          i.getNomeCompleto(),
          i.getNickname(),
          i.getEmail(),
          i.isPagamento(),
          i.getComprovantePagamento()));
    } catch (Exception e) {
      throw new RuntimeException("Erro ao buscar inscrições", e);
    }
  }
}

package com.unifucamp.gamearena.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.unifucamp.gamearena.dto.InscricaoResponse;
import com.unifucamp.gamearena.service.InscricaoService;

@RestController
public class InscricaoController {

  private final InscricaoService inscricaoService;

  public InscricaoController(InscricaoService inscricaoService) {
    this.inscricaoService = inscricaoService;
  }

  @GetMapping
  public ResponseEntity<Page<InscricaoResponse>> findAll(@PageableDefault(page = 0, size = 10) Pageable pageable) {
    return ResponseEntity.ok(inscricaoService.findAll(pageable));
  }

}

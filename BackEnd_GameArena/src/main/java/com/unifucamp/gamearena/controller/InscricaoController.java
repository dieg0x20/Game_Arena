package com.unifucamp.gamearena.controller;

import com.unifucamp.gamearena.controller.dto.InscricaoDto;
import com.unifucamp.gamearena.domain.Inscricao;
import com.unifucamp.gamearena.mapper.InscricaoMapper;
import com.unifucamp.gamearena.service.InscricaoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/inscricoes")
public class InscricaoController {

    private final InscricaoService inscricaoService;

    public InscricaoController(InscricaoService inscricaoService) {
        this.inscricaoService = inscricaoService;
    }

    @PostMapping("/registrar")
    public ResponseEntity<InscricaoDto> criarInscricao(@RequestBody @Valid InscricaoDto inscricaoDTO) {
        Inscricao inscricaoSalva = inscricaoService.salvar(InscricaoMapper.dtoToDomain(inscricaoDTO));
        return ResponseEntity.ok(InscricaoMapper.domainToDTO(inscricaoSalva));
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}")
    public ResponseEntity<InscricaoDto> atualizarInscricao(@PathVariable Integer id, @RequestBody InscricaoDto inscricaoDTO) {
        return inscricaoService.atualizar(id, InscricaoMapper.dtoToDomain(inscricaoDTO))
                .map(inscricao -> ResponseEntity.ok(InscricaoMapper.domainToDTO(inscricao)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/{id}")
    public ResponseEntity<InscricaoDto> buscarPorId(@PathVariable Integer id) {
        return inscricaoService.buscarPorId(id)
                .map(inscricao -> ResponseEntity.ok(InscricaoMapper.domainToDTO(inscricao)))
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<InscricaoDto>> listarTodas(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<Inscricao> inscricoes = inscricaoService.listarTodas(pageable);

        List<InscricaoDto> inscricoesDTO = inscricoes.stream()
                .map(InscricaoMapper::domainToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(inscricoesDTO);
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> desativarInscricao(@PathVariable Integer id) {
        boolean desativado = inscricaoService.desativar(id);
        if (desativado) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

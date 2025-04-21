package com.unifucamp.gamearena.service;

import com.unifucamp.gamearena.domain.Inscricao;
import com.unifucamp.gamearena.mapper.InscricaoMapper;
import com.unifucamp.gamearena.repository.entity.InscricaoEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InscricaoService {

    private final com.unifucamp.gamearena.repository.InscricaoRepository inscricaoRepository;

    public InscricaoService(com.unifucamp.gamearena.repository.InscricaoRepository inscricaoRepository) {
        this.inscricaoRepository = inscricaoRepository;
    }

    public Inscricao salvar(Inscricao inscricao) {
        inscricao.setDataInscricao(LocalDateTime.now());
        InscricaoEntity inscricacaoSalva = inscricaoRepository.save(InscricaoMapper.domainToEntity(inscricao));
        return InscricaoMapper.entityToDomain(inscricacaoSalva);
    }

    public Optional<Inscricao> buscarPorId(Integer id) {
        return inscricaoRepository.findById(id).map(InscricaoMapper::entityToDomain);
    }

    public List<Inscricao> listarTodas(Pageable pageable) {
        return inscricaoRepository.findAll(pageable).stream()
                .map(InscricaoMapper::entityToDomain)
                .toList();
    }

    public Optional<Inscricao> atualizar(Integer id, Inscricao inscricao) {
        return inscricaoRepository.findById(id)
                .map(entity -> {
                    InscricaoMapper.atualizarInscricao(inscricao, entity);
                    return inscricaoRepository.save(entity);
                })
                .map(InscricaoMapper::entityToDomain);
    }

    public boolean desativar(Integer id) {
        return inscricaoRepository.findById(id)
                .map(entity -> {
                    entity.setAtivo(false);
                    inscricaoRepository.save(entity);
                    return true;
                }).orElse(false);
    }
}

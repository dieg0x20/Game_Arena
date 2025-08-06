package com.unifucamp.gamearena.service;

import com.unifucamp.gamearena.domain.Inscricao;
import com.unifucamp.gamearena.entity.InscricaoEntity;
import com.unifucamp.gamearena.mapper.InscricaoMapper;
import com.unifucamp.gamearena.repository.InscricaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class InscricaoService {

    private static final Logger log = LoggerFactory.getLogger(InscricaoService.class);

    private final InscricaoRepository inscricaoRepository;
    private final EmailService emailService;

    public InscricaoService(InscricaoRepository inscricaoRepository, EmailService emailService) {
        this.inscricaoRepository = inscricaoRepository;
        this.emailService = emailService;
    }

    public Inscricao salvar(Inscricao inscricao) {
        log.info("Iniciando processo de salvar inscrição: {}", inscricao);
        inscricao.setDataInscricao(LocalDateTime.now());
        InscricaoEntity inscricacaoSalva = inscricaoRepository.save(InscricaoMapper.domainToEntity(inscricao));
        log.info("Inscrição salva com sucesso. ID: {}", inscricacaoSalva.getId());
        return InscricaoMapper.entityToDomain(inscricacaoSalva);
    }

    public Optional<Inscricao> buscarPorId(Integer id) {
        log.info("Buscando inscrição com ID: {}", id);
        Optional<Inscricao> inscricaoEncontrada = inscricaoRepository.findById(id).map(InscricaoMapper::entityToDomain);
        if (inscricaoEncontrada.isPresent()) {
            log.info("Inscrição encontrada: {}", inscricaoEncontrada.get().getId());
        } else {
            log.warn("Nenhuma inscrição encontrada com ID: {}", id);
        }
        return inscricaoEncontrada;
    }

    public List<Inscricao> listarTodas(Pageable pageable) {
        log.info("Buscando inscrições");
        List<Inscricao> inscricoes = inscricaoRepository.findAll(pageable).stream()
                .map(InscricaoMapper::entityToDomain)
                .toList();
        log.info("Total de inscrições retornadas: {}", inscricoes.size());
        return inscricoes;
    }

    public Optional<Inscricao> atualizar(Integer id, Inscricao inscricao) {
        log.info("Atualizando inscrição com ID: {}", id);
        return inscricaoRepository.findById(id)
                .map(entity -> {
                    boolean pagamentoAntes = Boolean.TRUE.equals(entity.isPagamento());
                    boolean pagamentoNovo = Boolean.TRUE.equals(inscricao.getPagamento());

                    InscricaoMapper.atualizarInscricao(inscricao, entity);

                    InscricaoEntity pagamentoAtualizado = inscricaoRepository.save(entity);

                    log.info("Inscrição com ID {} atualizada com sucesso.", id);

                    if (!pagamentoAntes && pagamentoNovo) {
                        emailService.enviarConfirmacaoPagamento(pagamentoAtualizado.getEmail(), pagamentoAtualizado.getNickname());
                    }
                    return pagamentoAtualizado;
                })
                .map(InscricaoMapper::entityToDomain);
    }

    public boolean desativar(Integer id) {
        log.info("Desativando inscrição com ID: {}", id);
        return inscricaoRepository.findById(id)
                .map(entity -> {
                    entity.setAtivo(false);
                    inscricaoRepository.save(entity);
                    log.info("Inscrição com ID {} desativada com sucesso.", id);
                    return true;
                }).orElseGet(() -> {
                    log.warn("Inscrição com ID {} não encontrada para desativação.", id);
                    return false;
                });
    }
}

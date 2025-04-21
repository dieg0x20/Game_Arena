package com.unifucamp.gamearena.mapper;

import com.unifucamp.gamearena.controller.dto.InscricaoDTO;
import com.unifucamp.gamearena.domain.Inscricao;
import com.unifucamp.gamearena.repository.entity.InscricaoEntity;

public class InscricaoMapper {

    public static InscricaoDTO domainToDTO(Inscricao domain) {
        com.unifucamp.gamearena.controller.dto.InscricaoDTO dto = new com.unifucamp.gamearena.controller.dto.InscricaoDTO();
        dto.setId(domain.getId());
        dto.setNomeCompleto(domain.getNomeCompleto());
        dto.setNickname(domain.getNickname());
        dto.setEmail(domain.getEmail());
        dto.setDataNascimento(domain.getDataNascimento());
        dto.setComprovantePagamento(domain.getComprovantePagamento());
        dto.setAceitouTermos(domain.getAceitouTermos());
        dto.setDataInscricao(domain.getDataInscricao());
        dto.setAtivo(domain.getAtivo());
        dto.setPagamento(domain.getPagamento());
        return dto;
    }

    public static InscricaoEntity domainToEntity(Inscricao domain) {
        InscricaoEntity entity = new InscricaoEntity();
        entity.setId(domain.getId());
        entity.setNomeCompleto(domain.getNomeCompleto());
        entity.setNickname(domain.getNickname());
        entity.setEmail(domain.getEmail());
        entity.setDataNascimento(domain.getDataNascimento());
        entity.setComprovantePagamento(domain.getComprovantePagamento());
        entity.setAceitouTermos(domain.getAceitouTermos());
        entity.setDataInscricao(domain.getDataInscricao());
        entity.setAtivo(domain.getAtivo());
        entity.setPagamento(domain.getPagamento());
        return entity;
    }

    public static Inscricao entityToDomain(InscricaoEntity inscricaoEntity) {
        Inscricao domain = new Inscricao();
        domain.setId(inscricaoEntity.getId());
        domain.setNomeCompleto(inscricaoEntity.getNomeCompleto());
        domain.setNickname(inscricaoEntity.getNickname());
        domain.setEmail(inscricaoEntity.getEmail());
        domain.setDataNascimento(inscricaoEntity.getDataNascimento());
        domain.setComprovantePagamento(inscricaoEntity.getComprovantePagamento());
        domain.setAceitouTermos(inscricaoEntity.isAceitouTermos());
        domain.setDataInscricao(inscricaoEntity.getDataInscricao());
        domain.setAtivo(inscricaoEntity.isAtivo());
        domain.setPagamento(inscricaoEntity.isPagamento());
        return domain;
    }

    public static Inscricao dtoToDomain(InscricaoDTO incricaoDto) {
        Inscricao domain = new Inscricao();
        domain.setId(incricaoDto.getId());
        domain.setNomeCompleto(incricaoDto.getNomeCompleto());
        domain.setNickname(incricaoDto.getNickname());
        domain.setEmail(incricaoDto.getEmail());
        domain.setDataNascimento(incricaoDto.getDataNascimento());
        domain.setComprovantePagamento(incricaoDto.getComprovantePagamento());
        domain.setAceitouTermos(incricaoDto.getAceitouTermos());
        domain.setDataInscricao(incricaoDto.getDataInscricao());
        domain.setAtivo(incricaoDto.getAtivo());
        domain.setPagamento(incricaoDto.getPagamento());
        return domain;
    }

    public static void atualizarInscricao(Inscricao inscricao, InscricaoEntity entity) {
        if (inscricao.getNomeCompleto() != null) {
            entity.setNomeCompleto(inscricao.getNomeCompleto());
        }
        if (inscricao.getNickname() != null) {
            entity.setNickname(inscricao.getNickname());
        }
        if (inscricao.getEmail() != null) {
            entity.setEmail(inscricao.getEmail());
        }
        if (inscricao.getDataNascimento() != null) {
            entity.setDataNascimento(inscricao.getDataNascimento());
        }
        if (inscricao.getComprovantePagamento() != null) {
            entity.setComprovantePagamento(inscricao.getComprovantePagamento());
        }
        if (inscricao.getAceitouTermos() != null) {
            entity.setAceitouTermos(inscricao.getAceitouTermos());
        }
        if (inscricao.getDataInscricao() != null) {
            entity.setDataInscricao(inscricao.getDataInscricao());
        }
        if (inscricao.getAtivo() != null) {
            entity.setAtivo(inscricao.getAtivo());
        }
        if (inscricao.getPagamento() != null) {
            entity.setPagamento(inscricao.getPagamento());
        }
    }
}

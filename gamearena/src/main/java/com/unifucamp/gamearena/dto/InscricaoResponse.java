package com.unifucamp.gamearena.dto;

public record InscricaoResponse(
        Long id,
        String nomeCompleto,
        String nickname,
        String email,
        boolean pagamento,
        String comprovantePagamento) {
}
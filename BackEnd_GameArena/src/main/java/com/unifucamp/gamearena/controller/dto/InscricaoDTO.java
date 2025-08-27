package com.unifucamp.gamearena.controller.dto;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class InscricaoDTO {

    private Integer id;

    @NotBlank(message = "O nome completo é obrigatório")
    private String nomeCompleto;

    @NotBlank(message = "O nickname é obrigatório")
    private String nickname;

    @Email(message = "O e-mail informado não é válido")
    @NotBlank(message = "O e-mail é obrigatório")
    private String email;

    @NotNull(message = "A data de nascimento é obrigatória")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dataNascimento;

    @NotBlank(message = "O comprovante de pagamento é obrigatório")
    private String comprovantePagamento;

    @NotNull(message = "Aceitar os termos é obrigatório")
    private Boolean aceitouTermos;

    private LocalDateTime dataInscricao;

    private Boolean ativo;

    private Boolean pagamento;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getComprovantePagamento() {
        return comprovantePagamento;
    }

    public void setComprovantePagamento(String comprovantePagamento) {
        this.comprovantePagamento = comprovantePagamento;
    }

    public Boolean getAceitouTermos() {
        return aceitouTermos;
    }

    public void setAceitouTermos(Boolean aceitouTermos) {
        this.aceitouTermos = aceitouTermos;
    }

    public LocalDateTime getDataInscricao() {
        return dataInscricao;
    }

    public void setDataInscricao(LocalDateTime dataInscricao) {
        this.dataInscricao = dataInscricao;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public Boolean getPagamento() {
        return pagamento;
    }

    public void setPagamento(Boolean pagamento) {
        this.pagamento = pagamento;
    }
}

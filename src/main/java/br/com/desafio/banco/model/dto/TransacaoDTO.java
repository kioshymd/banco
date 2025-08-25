package br.com.desafio.banco.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class TransacaoDTO {
    @NotBlank
    @JsonProperty("forma_pagamento")
    public String formaPagamento;

    @NotNull
    @JsonProperty("numero_conta")
    public Long numeroConta;

    @NotNull
    @Positive
    @JsonProperty("valor")
    public BigDecimal valor;
}

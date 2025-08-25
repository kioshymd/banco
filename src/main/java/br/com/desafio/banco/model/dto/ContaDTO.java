package br.com.desafio.banco.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class ContaDTO {

    @NotNull
    @JsonProperty("numero_conta")
    public Long numeroConta;

    @NotNull
    @JsonProperty("saldo")
    public BigDecimal saldo;
}

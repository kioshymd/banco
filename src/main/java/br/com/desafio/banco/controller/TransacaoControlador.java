package br.com.desafio.banco.controller;

import br.com.desafio.banco.model.Conta;
import br.com.desafio.banco.model.dto.TransacaoDTO;
import br.com.desafio.banco.service.TransacaoServico;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/transacao")
public class TransacaoControlador {
    private final TransacaoServico serv;

    public TransacaoControlador(TransacaoServico s) {
        this.serv = s;
    }

    @PostMapping
    public ResponseEntity<?> transacionar(@RequestBody @Valid TransacaoDTO dto) {
        try {
            Conta c = serv.processar(dto.formaPagamento, dto.numeroConta, dto.valor);
            return ResponseEntity.status(201).body(Map.of("numero_conta", c.getNumeroConta(), "saldo", c.getSaldo()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(Map.of("erro", e.getMessage()));
        } catch (RuntimeException e) {
            if ("SALDO_INSUFICIENTE".equals(e.getMessage()))
                return ResponseEntity.status(404).body(Map.of("erro", "Saldo indisponível"));
            throw e;
        }
    }
}

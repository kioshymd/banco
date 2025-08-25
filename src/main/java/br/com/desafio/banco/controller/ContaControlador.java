package br.com.desafio.banco.controller;

import br.com.desafio.banco.model.Conta;
import br.com.desafio.banco.model.dto.ContaDTO;
import br.com.desafio.banco.service.ContaServico;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/conta")
public class ContaControlador {
    private final ContaServico serv;

    public ContaControlador(ContaServico s) {
        this.serv = s;
    }

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody @Valid ContaDTO body) {
        try {
            Conta c = serv.criar(body.numeroConta, body.saldo);
            return ResponseEntity.created(URI.create("/conta?numero_conta=" + c.getNumeroConta())).body(Map.of("numero_conta", c.getNumeroConta(), "saldo", c.getSaldo()));
        } catch (IllegalStateException e) {
            return ResponseEntity.status(409).body(Map.of("erro", "Conta já existe"));
        }
    }

    @GetMapping
    public ResponseEntity<?> buscar(@RequestParam("numero_conta") Long numeroConta) {
        return serv.buscarPorNumero(numeroConta).<ResponseEntity<?>>map(c -> ResponseEntity.ok(Map.of("numero_conta", c.getNumeroConta(), "saldo", c.getSaldo()))).orElseGet(() -> ResponseEntity.status(404).build());
    }
}

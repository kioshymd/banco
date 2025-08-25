package br.com.desafio.banco.service;

import br.com.desafio.banco.model.Conta;
import br.com.desafio.banco.repository.ContaRepositorio;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class ContaServico {
    private final ContaRepositorio repo;

    public ContaServico(ContaRepositorio r) {
        this.repo = r;
    }

    public Conta criar(Long numeroConta, BigDecimal saldo) {
        if (repo.existsByNumeroConta(numeroConta)) throw new IllegalStateException("Conta já existe");
        Conta c = new Conta();
        c.setNumeroConta(numeroConta);
        c.setSaldo(saldo);
        return repo.save(c);
    }

    public Optional<Conta> buscarPorNumero(Long numeroConta) {
        return repo.findByNumeroConta(numeroConta);
    }
}

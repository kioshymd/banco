package br.com.desafio.banco.service;

import br.com.desafio.banco.model.*;
import br.com.desafio.banco.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class TransacaoServico {
    private final ContaRepositorio contaRepo;
    private final TransacaoRepositorio txRepo;

    public TransacaoServico(ContaRepositorio c, TransacaoRepositorio t) {
        this.contaRepo = c;
        this.txRepo = t;
    }

    private BigDecimal taxa(Transacao.Forma f, BigDecimal v) {
        return switch (f) {
            case DEBITO -> v.multiply(new BigDecimal("0.03"));
            case CREDITO -> v.multiply(new BigDecimal("0.05"));
            case PIX -> BigDecimal.ZERO;
        };
    }

    @Transactional
    public Conta processar(String formaStr, Long numeroConta, BigDecimal valor) {
        Transacao.Forma f;
        try {
            f = Transacao.Forma.valueOf(formaStr);
        } catch (Exception e) {
            throw new IllegalArgumentException("forma_pagamento inválida: use DEBITO, CREDITO ou PIX");
        }
        Conta conta = contaRepo.findByNumeroConta(numeroConta).orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
        BigDecimal total = valor.add(taxa(f, valor)).setScale(2, RoundingMode.HALF_UP);

        if (conta.getSaldo().compareTo(total) < 0)
            throw new RuntimeException("SALDO_INSUFICIENTE");

        conta.setSaldo(conta.getSaldo().subtract(total));
        contaRepo.save(conta);
        Transacao tx = new Transacao();
        tx.setForma(f);
        tx.setConta(conta);
        tx.setValorTotalDebitado(total);
        txRepo.save(tx);
        return conta;
    }
}

package br.com.desafio.banco.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "transacoes")
public class Transacao {
    public enum Forma {DEBITO, CREDITO, PIX}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 1)
    private Forma forma;

    @ManyToOne(optional = false)
    @JoinColumn(name = "conta_id")
    private Conta conta;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal valorTotalDebitado;

    @Column(nullable = false, updatable = false)
    private OffsetDateTime criadaEm = OffsetDateTime.now();

    public Long getId() {
        return id;
    }

    public Forma getForma() {
        return forma;
    }

    public void setForma(Forma f) {
        forma = f;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta c) {
        conta = c;
    }

    public BigDecimal getValorTotalDebitado() {
        return valorTotalDebitado;
    }

    public void setValorTotalDebitado(BigDecimal v) {
        valorTotalDebitado = v;
    }

    public OffsetDateTime getCriadaEm() {
        return criadaEm;
    }
}

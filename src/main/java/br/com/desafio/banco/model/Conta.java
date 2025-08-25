package br.com.desafio.banco.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "contas", uniqueConstraints = @UniqueConstraint(columnNames = "numeroConta"))
public class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long numeroConta;

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal saldo = BigDecimal.ZERO;

    public Long getId() {
        return id;
    }

    public Long getNumeroConta() {
        return numeroConta;
    }

    public void setNumeroConta(Long n) {
        numeroConta = n;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal s) {
        saldo = s;
    }
}

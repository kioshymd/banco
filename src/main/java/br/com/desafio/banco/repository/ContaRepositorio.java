package br.com.desafio.banco.repository;

import br.com.desafio.banco.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContaRepositorio extends JpaRepository<Conta, Long> {
    Optional<Conta> findByNumeroConta(Long numeroConta);

    boolean existsByNumeroConta(Long numeroConta);
}

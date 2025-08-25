package br.com.desafio.banco.repository;

import br.com.desafio.banco.model.Transacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransacaoRepositorio extends JpaRepository<Transacao, Long> {
}

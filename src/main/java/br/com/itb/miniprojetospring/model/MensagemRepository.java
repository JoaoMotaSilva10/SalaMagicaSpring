package br.com.itb.miniprojetospring.model;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MensagemRepository extends JpaRepository<Mensagem, Integer> {
}

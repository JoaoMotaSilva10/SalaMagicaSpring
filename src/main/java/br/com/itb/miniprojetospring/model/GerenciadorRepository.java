package br.com.itb.miniprojetospring.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GerenciadorRepository extends JpaRepository<Gerenciador, Long> {
    Optional<Gerenciador> findByEmail(String email);
}
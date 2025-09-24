package br.com.itb.miniprojetospring.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRecuperacaoRepository extends JpaRepository<TokenRecuperacao, Long> {
    Optional<TokenRecuperacao> findByToken(String token);
    Optional<TokenRecuperacao> findByTokenAndUsuario(String token, Usuario usuario);
    void deleteByUsuario(Usuario usuario);
}
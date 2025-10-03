package br.com.itb.miniprojetospring.service;

import br.com.itb.miniprojetospring.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.List;
import java.util.Random;

@Service
public class GerenciadorService {

    private final GerenciadorRepository gerenciadorRepository;
    private final TokenRecuperacaoRepository tokenRepository;
    private final EmailService emailService;

    public GerenciadorService(GerenciadorRepository gerenciadorRepository,
                             TokenRecuperacaoRepository tokenRepository,
                             EmailService emailService) {
        this.gerenciadorRepository = gerenciadorRepository;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
    }

    @Transactional
    public Gerenciador save(Gerenciador gerenciador) {
        String senhaBase64 = Base64.getEncoder().encodeToString(gerenciador.getSenha().getBytes());
        gerenciador.setSenha(senhaBase64);
        return gerenciadorRepository.save(gerenciador);
    }

    public List<Gerenciador> findAll() {
        return gerenciadorRepository.findAll();
    }

    public Gerenciador findByEmail(String email) {
        return gerenciadorRepository.findByEmail(email).orElse(null);
    }

    @Transactional
    public Gerenciador update(Long id, Gerenciador gerenciador) {
        return gerenciadorRepository.findById(id)
                .map(g -> {
                    g.setNome(gerenciador.getNome());
                    g.setEmail(gerenciador.getEmail());
                    if (gerenciador.getSenha() != null && !gerenciador.getSenha().isEmpty()) {
                        String senhaBase64 = Base64.getEncoder().encodeToString(gerenciador.getSenha().getBytes());
                        g.setSenha(senhaBase64);
                    }
                    g.setUnidade(gerenciador.getUnidade());
                    g.setDepartamento(gerenciador.getDepartamento());
                    g.setCargo(gerenciador.getCargo());
                    if (gerenciador.getStatusUsuario() != null) {
                        g.setStatusUsuario(gerenciador.getStatusUsuario());
                    }
                    return gerenciadorRepository.save(g);
                })
                .orElseThrow(() -> new RuntimeException("Gerenciador não encontrado"));
    }

    @Transactional
    public void delete(Long id) {
        if (!gerenciadorRepository.existsById(id)) {
            throw new RuntimeException("Gerenciador não encontrado");
        }
        gerenciadorRepository.deleteById(id);
    }

    @Transactional
    public void solicitarRecuperacaoSenha(String email) {
        Gerenciador gerenciador = findByEmail(email);
        if (gerenciador == null) {
            throw new RuntimeException("Email não encontrado");
        }

        String token = String.format("%06d", new Random().nextInt(999999));
        TokenRecuperacao tokenRecuperacao = new TokenRecuperacao();
        tokenRecuperacao.setToken(token);
        tokenRecuperacao.setUsuario(gerenciador);
        tokenRepository.save(tokenRecuperacao);

        try {
            emailService.enviarEmailRecuperacao(email, token);
        } catch (Exception e) {
            System.out.println("Erro ao enviar e-mail, mas token foi gerado: " + token);
        }
    }
}
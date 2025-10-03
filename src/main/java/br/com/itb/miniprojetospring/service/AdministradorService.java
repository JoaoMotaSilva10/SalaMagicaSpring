package br.com.itb.miniprojetospring.service;

import br.com.itb.miniprojetospring.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;
import java.util.List;
import java.util.Random;

@Service
public class AdministradorService {

    private final AdministradorRepository administradorRepository;
    private final TokenRecuperacaoRepository tokenRepository;
    private final EmailService emailService;

    public AdministradorService(AdministradorRepository administradorRepository,
                               TokenRecuperacaoRepository tokenRepository,
                               EmailService emailService) {
        this.administradorRepository = administradorRepository;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
    }

    @Transactional
    public Administrador save(Administrador administrador) {
        String senhaBase64 = Base64.getEncoder().encodeToString(administrador.getSenha().getBytes());
        administrador.setSenha(senhaBase64);
        return administradorRepository.save(administrador);
    }

    public List<Administrador> findAll() {
        return administradorRepository.findAll();
    }

    public Administrador findByEmail(String email) {
        return administradorRepository.findByEmail(email).orElse(null);
    }

    @Transactional
    public void solicitarRecuperacaoSenha(String email) {
        Administrador administrador = findByEmail(email);
        if (administrador == null) {
            throw new RuntimeException("Email não encontrado");
        }

        String token = String.format("%06d", new Random().nextInt(999999));
        TokenRecuperacao tokenRecuperacao = new TokenRecuperacao();
        tokenRecuperacao.setToken(token);
        tokenRecuperacao.setUsuario(administrador);
        tokenRepository.save(tokenRecuperacao);

        try {
            emailService.enviarEmailRecuperacao(email, token);
        } catch (Exception e) {
            System.out.println("Erro ao enviar e-mail, mas token foi gerado: " + token);
        }
    }
}
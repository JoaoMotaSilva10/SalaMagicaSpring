package br.com.itb.miniprojetospring.service;

import br.com.itb.miniprojetospring.model.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Random;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final TokenRecuperacaoRepository tokenRepository;
    private final EmailService emailService;

    public AlunoService(AlunoRepository alunoRepository, 
                       TokenRecuperacaoRepository tokenRepository,
                       EmailService emailService) {
        this.alunoRepository = alunoRepository;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
    }

    @Transactional
    public Aluno save(Aluno aluno) {
        String senhaBase64 = Base64.getEncoder().encodeToString(aluno.getSenha().getBytes());
        aluno.setSenha(senhaBase64);
        return alunoRepository.save(aluno);
    }

    public List<Aluno> findAll() {
        return alunoRepository.findAll();
    }

    public Aluno findByEmail(String email) {
        return alunoRepository.findByEmail(email).orElse(null);
    }

    public Aluno findByRm(String rm) {
        return alunoRepository.findByRm(rm).orElse(null);
    }

    @Transactional
    public Aluno update(Long id, Aluno aluno) {
        return alunoRepository.findById(id)
                .map(a -> {
                    if (aluno.getNome() != null && !aluno.getNome().isEmpty()) {
                        a.setNome(aluno.getNome());
                    }
                    if (aluno.getEmail() != null && !aluno.getEmail().isEmpty()) {
                        a.setEmail(aluno.getEmail());
                    }
                    if (aluno.getSenha() != null && !aluno.getSenha().isEmpty()) {
                        String senhaBase64 = Base64.getEncoder().encodeToString(aluno.getSenha().getBytes());
                        a.setSenha(senhaBase64);
                    }
                    if (aluno.getRm() != null) {
                        a.setRm(aluno.getRm());
                    }
                    if (aluno.getTurma() != null) {
                        a.setTurma(aluno.getTurma());
                    }
                    if (aluno.getSerie() != null) {
                        a.setSerie(aluno.getSerie());
                    }
                    if (aluno.getPeriodo() != null) {
                        a.setPeriodo(aluno.getPeriodo());
                    }
                    if (aluno.getCpf() != null) {
                        a.setCpf(aluno.getCpf());
                    }
                    if (aluno.getUnidade() != null) {
                        a.setUnidade(aluno.getUnidade());
                    }
                    if (aluno.getStatusUsuario() != null) {
                        a.setStatusUsuario(aluno.getStatusUsuario());
                    }
                    return alunoRepository.save(a);
                })
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));
    }

    @Transactional
    public void delete(Long id) {
        if (!alunoRepository.existsById(id)) {
            throw new RuntimeException("Aluno não encontrado");
        }
        alunoRepository.deleteById(id);
    }

    @Transactional
    public void solicitarRecuperacaoSenha(String email) {
        Aluno aluno = findByEmail(email);
        if (aluno == null) {
            throw new RuntimeException("Email não encontrado");
        }

        String token = String.format("%06d", new Random().nextInt(999999));
        TokenRecuperacao tokenRecuperacao = new TokenRecuperacao();
        tokenRecuperacao.setToken(token);
        tokenRecuperacao.setUsuario(aluno);
        tokenRepository.save(tokenRecuperacao);

        try {
            emailService.enviarEmailRecuperacao(email, token);
        } catch (Exception e) {
            System.out.println("Erro ao enviar e-mail, mas token foi gerado: " + token);
        }
    }

    @Transactional
    public void verificarCodigo(String email, String codigo) {
        Aluno aluno = findByEmail(email);
        if (aluno == null) {
            throw new RuntimeException("Email não encontrado");
        }

        TokenRecuperacao token = tokenRepository.findByTokenAndUsuario(codigo, aluno)
                .orElseThrow(() -> new RuntimeException("Código inválido"));

        if (token.isUsado()) {
            throw new RuntimeException("Código já foi usado");
        }

        if (token.getDataExpiracao().isBefore(java.time.LocalDateTime.now())) {
            throw new RuntimeException("Código expirado");
        }
    }

    @Transactional
    public void redefinirSenhaComCodigo(String email, String codigo, String novaSenha) {
        verificarCodigo(email, codigo);
        
        Aluno aluno = findByEmail(email);
        TokenRecuperacao token = tokenRepository.findByTokenAndUsuario(codigo, aluno).get();
        
        String senhaBase64 = Base64.getEncoder().encodeToString(novaSenha.getBytes());
        aluno.setSenha(senhaBase64);
        alunoRepository.save(aluno);
        
        token.setUsado(true);
        tokenRepository.save(token);
    }
}
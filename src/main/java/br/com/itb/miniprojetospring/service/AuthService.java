package br.com.itb.miniprojetospring.service;

import br.com.itb.miniprojetospring.config.JwtUtil;
import br.com.itb.miniprojetospring.model.*;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {

    private final AlunoRepository alunoRepository;
    private final GerenciadorRepository gerenciadorRepository;
    private final AdministradorRepository administradorRepository;
    private final JwtUtil jwtUtil;

    public AuthService(AlunoRepository alunoRepository,
                      GerenciadorRepository gerenciadorRepository,
                      AdministradorRepository administradorRepository,
                      JwtUtil jwtUtil) {
        this.alunoRepository = alunoRepository;
        this.gerenciadorRepository = gerenciadorRepository;
        this.administradorRepository = administradorRepository;
        this.jwtUtil = jwtUtil;
    }

    public Map<String, Object> login(String email, String senha) {
        String senhaBase64 = Base64.getEncoder().encodeToString(senha.getBytes());
        Pessoa pessoa = null;

        // Tenta encontrar em cada tipo de usuário
        Aluno aluno = alunoRepository.findByEmail(email).orElse(null);
        if (aluno != null && aluno.getSenha().equals(senhaBase64)) {
            pessoa = aluno;
        }

        if (pessoa == null) {
            Gerenciador gerenciador = gerenciadorRepository.findByEmail(email).orElse(null);
            if (gerenciador != null && gerenciador.getSenha().equals(senhaBase64)) {
                pessoa = gerenciador;
            }
        }

        if (pessoa == null) {
            Administrador administrador = administradorRepository.findByEmail(email).orElse(null);
            if (administrador != null && administrador.getSenha().equals(senhaBase64)) {
                pessoa = administrador;
            }
        }

        if (pessoa != null) {
            String token = jwtUtil.generateToken(pessoa.getEmail(), pessoa.getTipoUsuario(), pessoa.getId());
            
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("usuario", pessoa);
            return response;
        }

        return null;
    }
}
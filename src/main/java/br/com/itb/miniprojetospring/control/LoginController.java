package br.com.itb.miniprojetospring.control;

import br.com.itb.miniprojetospring.model.*;
import br.com.itb.miniprojetospring.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
public class LoginController {

    private final AlunoRepository alunoRepository;
    private final GerenciadorRepository gerenciadorRepository;
    private final AdministradorRepository administradorRepository;
    private final AuthService authService;

    public LoginController(AlunoRepository alunoRepository,
                          GerenciadorRepository gerenciadorRepository,
                          AdministradorRepository administradorRepository,
                          AuthService authService) {
        this.alunoRepository = alunoRepository;
        this.gerenciadorRepository = gerenciadorRepository;
        this.administradorRepository = administradorRepository;
        this.authService = authService;
    }

    @PostMapping("/alunos/login")
    public ResponseEntity<?> loginUnificado(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String senha = credentials.get("senha");
        String senhaBase64 = Base64.getEncoder().encodeToString(senha.getBytes());

        // Tenta encontrar em cada tipo de usuário
        Aluno aluno = alunoRepository.findByEmail(email).orElse(null);
        if (aluno != null && aluno.getSenha().equals(senhaBase64)) {
            return ResponseEntity.ok(aluno);
        }

        Gerenciador gerenciador = gerenciadorRepository.findByEmail(email).orElse(null);
        if (gerenciador != null && gerenciador.getSenha().equals(senhaBase64)) {
            return ResponseEntity.ok(gerenciador);
        }

        Administrador administrador = administradorRepository.findByEmail(email).orElse(null);
        if (administrador != null && administrador.getSenha().equals(senhaBase64)) {
            return ResponseEntity.ok(administrador);
        }

        return ResponseEntity.status(401).body("E-mail ou senha inválidos");
    }
}
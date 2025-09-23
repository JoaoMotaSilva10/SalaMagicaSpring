package br.com.itb.miniprojetospring.control;

import br.com.itb.miniprojetospring.model.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final UsuarioRepository usuarioRepository;

    public AuthController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // 📌 Endpoint: Solicitar redefinição (simulação, sem envio de e-mail real)
    @PostMapping("/esqueci-senha")
    public ResponseEntity<?> esqueciSenha(@RequestBody Map<String, String> request) {
        String email = request.get("email");

        if (email == null || email.isBlank()) {
            return ResponseEntity.badRequest().body("E-mail é obrigatório");
        }
        else{
            return ResponseEntity.ok("Se o e-mail existir, enviaremos instruções.");
        }


    }

    @PostMapping("/resetar-senha")
    public ResponseEntity<?> resetarSenha(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String novaSenha = request.get("novaSenha");

        if (email == null || email.isBlank() || novaSenha == null || novaSenha.isBlank()) {
            return ResponseEntity.badRequest().body("E-mail e nova senha são obrigatórios");
        }

        return usuarioRepository.findByEmail(email)
                .map(usuario -> {
                    usuario.setSenha(Base64.getEncoder().encodeToString(novaSenha.getBytes()));
                    usuarioRepository.save(usuario);
                    return ResponseEntity.ok("Senha redefinida com sucesso!");
                })
                .orElseGet(() -> ResponseEntity.badRequest().body("Usuário não encontrado"));
    }
}

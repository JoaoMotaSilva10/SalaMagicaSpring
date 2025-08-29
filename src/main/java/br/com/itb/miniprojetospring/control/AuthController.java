package br.com.itb.miniprojetospring.control;

import br.com.itb.miniprojetospring.model.AuthResponse;
import br.com.itb.miniprojetospring.model.ResetSenhaRequest;
import br.com.itb.miniprojetospring.model.Usuario;
import br.com.itb.miniprojetospring.model.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioRepository usuarioRepository;

    public AuthController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // ---------------- LOGIN ----------------
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String senha) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(401).body("Usuário não encontrado");
        }

        Usuario usuario = usuarioOpt.get();

        // senha do banco está em Base64
        String senhaDecodificada = new String(Base64.getDecoder().decode(usuario.getSenha()), StandardCharsets.UTF_8);

        if (!senha.equals(senhaDecodificada)) {
            return ResponseEntity.status(401).body("Senha incorreta");
        }

        // Retorno em AuthResponse
        return ResponseEntity.ok(new AuthResponse(
                usuario.getId().intValue(),
                usuario.getEmail(),
                usuario.getNivelAcesso()
        ));
    }

    // ---------------- RESETAR SENHA ----------------
    @PostMapping("/resetar-senha")
    public ResponseEntity<?> resetarSenha(@RequestBody ResetSenhaRequest request) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(request.getEmail());

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Usuário não encontrado");
        }

        Usuario usuario = usuarioOpt.get();

        // Criptografa a nova senha em Base64
        String senhaCriptografada = Base64.getEncoder().encodeToString(request.getNovaSenha().getBytes(StandardCharsets.UTF_8));
        usuario.setSenha(senhaCriptografada);
        usuarioRepository.save(usuario);

        return ResponseEntity.ok(new AuthResponse(
                usuario.getId().intValue(),
                usuario.getEmail(),
                usuario.getNivelAcesso()
        ));
    }
}


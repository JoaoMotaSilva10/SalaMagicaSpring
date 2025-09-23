package br.com.itb.miniprojetospring.control;

import br.com.itb.miniprojetospring.model.Usuario;
import br.com.itb.miniprojetospring.service.UsuarioService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public Usuario create(@RequestBody Usuario usuario) {
        return usuarioService.save(usuario);
    }

    @GetMapping
    public List<Usuario> listarTodos() {
        return usuarioService.listarTodos();
    }

    @PutMapping("/{id}")
    public Usuario update(@PathVariable Long id, @RequestBody Usuario usuario) {
        return usuarioService.update(id, usuario);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        usuarioService.delete(id);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        Usuario usuarioExistente = usuarioService.findByEmail(usuario.getEmail());

        if (usuarioExistente != null) {
            // Compara senha normal recebida codificada com a armazenada
            String senhaBase64 = Base64.getEncoder().encodeToString(usuario.getSenha().getBytes());
            if (usuarioExistente.getSenha().equals(senhaBase64)) {
                return ResponseEntity.ok(usuarioExistente);
            }
        }

        return ResponseEntity.status(401).body("E-mail ou senha inválidos");
    }

    @GetMapping("/perfil")
    public Usuario buscarPerfil(@RequestParam String email) {
        return usuarioService.findByEmail(email);
    }

    @PostMapping("/esqueci-senha")
    public ResponseEntity<String> esqueciSenha(@RequestParam String email) {
        try {
            usuarioService.solicitarRecuperacaoSenha(email);
            return ResponseEntity.ok("Código de recuperação enviado para o email");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PostMapping("/redefinir-senha")
    public ResponseEntity<String> redefinirSenha(@RequestParam String token, @RequestParam String novaSenha) {
        try {
            usuarioService.redefinirSenha(token, novaSenha);
            return ResponseEntity.ok("Senha redefinida com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return ResponseEntity.status(409).body("Email já cadastrado");
    }
}

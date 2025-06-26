package br.com.itb.miniprojetospring.control;

import br.com.itb.miniprojetospring.model.Usuario;
import br.com.itb.miniprojetospring.service.UsuarioService;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Usuario> create(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.save(usuario));
    }

    @GetMapping
    public ResponseEntity<List<Usuario>> findAll() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> findById(@PathVariable Integer id) {
        return usuarioService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Usuario> update(@PathVariable Integer id, @RequestBody Usuario usuario) {
        return ResponseEntity.ok(usuarioService.update(id, usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ Autenticação
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario usuario) {
        Optional<Usuario> usuarioOpt = usuarioService.authenticate(usuario.getEmail(), usuario.getSenha());

        if (usuarioOpt.isPresent()) {
            Usuario u = usuarioOpt.get();
            if ("ATIVO".equals(u.getStatusUsuario())) {
                return ResponseEntity.ok(new AuthResponse(u.getId(), u.getEmail(), u.getNivelAcesso()));
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Conta não está ativa.");
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou senha incorretos.");
    }
}

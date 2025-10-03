package br.com.itb.miniprojetospring.control;

import br.com.itb.miniprojetospring.model.Gerenciador;
import br.com.itb.miniprojetospring.service.GerenciadorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/gerenciadores")
@CrossOrigin(origins = "*")
public class GerenciadorController {

    private final GerenciadorService gerenciadorService;

    public GerenciadorController(GerenciadorService gerenciadorService) {
        this.gerenciadorService = gerenciadorService;
    }

    @PostMapping
    public Gerenciador create(@RequestBody Gerenciador gerenciador) {
        return gerenciadorService.save(gerenciador);
    }

    @GetMapping
    public List<Gerenciador> listarTodos() {
        return gerenciadorService.findAll();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Gerenciador gerenciador) {
        Gerenciador gerenciadorExistente = gerenciadorService.findByEmail(gerenciador.getEmail());

        if (gerenciadorExistente != null) {
            String senhaBase64 = Base64.getEncoder().encodeToString(gerenciador.getSenha().getBytes());
            if (gerenciadorExistente.getSenha().equals(senhaBase64)) {
                return ResponseEntity.ok(gerenciadorExistente);
            }
        }

        return ResponseEntity.status(401).body("E-mail ou senha inválidos");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Gerenciador> update(@PathVariable Long id, @RequestBody Gerenciador gerenciador) {
        try {
            Gerenciador gerenciadorAtualizado = gerenciadorService.update(id, gerenciador);
            return ResponseEntity.ok(gerenciadorAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            gerenciadorService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).build();
        }
    }

    @PostMapping("/esqueci-senha")
    public ResponseEntity<String> esqueciSenha(@RequestBody java.util.Map<String, String> request) {
        try {
            String email = request.get("email");
            gerenciadorService.solicitarRecuperacaoSenha(email);
            return ResponseEntity.ok("Código de recuperação enviado para o email");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
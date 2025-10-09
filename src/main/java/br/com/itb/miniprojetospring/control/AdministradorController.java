package br.com.itb.miniprojetospring.control;

import br.com.itb.miniprojetospring.model.Administrador;
import br.com.itb.miniprojetospring.service.AdministradorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/administradores")
@CrossOrigin(origins = "*")
public class AdministradorController {

    private final AdministradorService administradorService;

    public AdministradorController(AdministradorService administradorService) {
        this.administradorService = administradorService;
    }

    @PostMapping
    public ResponseEntity<Administrador> create(@RequestBody Administrador administrador) {
        try {
            Administrador saved = administradorService.save(administrador);
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(null);
        }
    }

    @GetMapping
    public ResponseEntity<List<Administrador>> listarTodos() {
        try {
            return ResponseEntity.ok(administradorService.findAll());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Administrador> findById(@PathVariable Long id) {
        try {
            Administrador administrador = administradorService.findById(id);
            return ResponseEntity.ok(administrador);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Administrador> update(@PathVariable Long id, @RequestBody Administrador administrador) {
        try {
            Administrador updated = administradorService.update(id, administrador);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            administradorService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Administrador administrador) {
        Administrador administradorExistente = administradorService.findByEmail(administrador.getEmail());

        if (administradorExistente != null) {
            String senhaBase64 = Base64.getEncoder().encodeToString(administrador.getSenha().getBytes());
            if (administradorExistente.getSenha().equals(senhaBase64)) {
                return ResponseEntity.ok(administradorExistente);
            }
        }

        return ResponseEntity.status(401).body("E-mail ou senha inválidos");
    }

    @PostMapping("/esqueci-senha")
    public ResponseEntity<String> esqueciSenha(@RequestBody java.util.Map<String, String> request) {
        try {
            String email = request.get("email");
            administradorService.solicitarRecuperacaoSenha(email);
            return ResponseEntity.ok("Código de recuperação enviado para o email");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }
}
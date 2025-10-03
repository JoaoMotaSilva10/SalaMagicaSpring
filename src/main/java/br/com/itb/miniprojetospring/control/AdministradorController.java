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
    public Administrador create(@RequestBody Administrador administrador) {
        return administradorService.save(administrador);
    }

    @GetMapping
    public List<Administrador> listarTodos() {
        return administradorService.findAll();
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
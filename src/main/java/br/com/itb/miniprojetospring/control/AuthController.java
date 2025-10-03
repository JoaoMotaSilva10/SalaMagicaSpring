package br.com.itb.miniprojetospring.control;

import br.com.itb.miniprojetospring.config.JwtUtil;
import br.com.itb.miniprojetospring.model.Pessoa;
import br.com.itb.miniprojetospring.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthService authService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String email = credentials.get("email");
        String senha = credentials.get("senha");

        Map<String, Object> response = authService.login(email, senha);
        
        if (response != null) {
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(401).body("E-mail ou senha inválidos");
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            
            if (jwtUtil.validateToken(token)) {
                Map<String, Object> tokenInfo = new HashMap<>();
                tokenInfo.put("valid", true);
                tokenInfo.put("email", jwtUtil.getEmailFromToken(token));
                tokenInfo.put("tipoUsuario", jwtUtil.getTipoUsuarioFromToken(token));
                tokenInfo.put("id", jwtUtil.getIdFromToken(token));
                return ResponseEntity.ok(tokenInfo);
            }
        }
        
        return ResponseEntity.status(401).body(Map.of("valid", false));
    }
}
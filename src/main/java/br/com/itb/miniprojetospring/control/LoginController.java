package br.com.itb.miniprojetospring.control;

import br.com.itb.miniprojetospring.model.Funcionario;
import br.com.itb.miniprojetospring.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private FuncionarioService funcionarioService;

    @PostMapping
    public ResponseEntity<String> login(@RequestBody Funcionario funcionario) {
        // Chama o serviço para autenticação
        Optional<Funcionario> funcionarioOptional = funcionarioService.authenticate(funcionario.getEmail(), funcionario.getSenha());

        if (funcionarioOptional.isPresent()) {
            // Aqui você pode retornar um token de autenticação ou apenas uma mensagem de sucesso
            return ResponseEntity.ok("Login realizado com sucesso!");
        }

        return ResponseEntity.status(401).body("Email ou senha incorretos.");
    }
}

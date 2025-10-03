package br.com.itb.miniprojetospring.control;

import br.com.itb.miniprojetospring.model.Aluno;
import br.com.itb.miniprojetospring.service.AlunoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alunos")
@CrossOrigin(origins = "*")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @PostMapping
    public Aluno create(@RequestBody Aluno aluno) {
        return alunoService.save(aluno);
    }

    @GetMapping
    public List<Aluno> listarTodos() {
        return alunoService.findAll();
    }

    @GetMapping("/perfil")
    public ResponseEntity<Aluno> buscarPerfil(@RequestParam String email) {
        try {
            Aluno aluno = alunoService.findByEmail(email);
            if (aluno != null) {
                return ResponseEntity.ok(aluno);
            }
            return ResponseEntity.status(404).body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aluno> update(@PathVariable Long id, @RequestBody Aluno aluno) {
        try {
            Aluno alunoAtualizado = alunoService.update(id, aluno);
            return ResponseEntity.ok(alunoAtualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        try {
            alunoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).build();
        }
    }

    @PostMapping("/esqueci-senha")
    public ResponseEntity<String> esqueciSenha(@RequestBody java.util.Map<String, String> request) {
        try {
            String email = request.get("email");
            alunoService.solicitarRecuperacaoSenha(email);
            return ResponseEntity.ok("Código de recuperação enviado para o email");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    @PostMapping("/verificar-codigo")
    public ResponseEntity<String> verificarCodigo(@RequestBody java.util.Map<String, String> request) {
        try {
            String email = request.get("email");
            String codigo = request.get("codigo");
            alunoService.verificarCodigo(email, codigo);
            return ResponseEntity.ok("Código válido");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    @PostMapping("/redefinir-senha")
    public ResponseEntity<String> redefinirSenha(@RequestBody java.util.Map<String, String> request) {
        try {
            String email = request.get("email");
            String codigo = request.get("codigo");
            String novaSenha = request.get("novaSenha");
            alunoService.redefinirSenhaComCodigo(email, codigo, novaSenha);
            return ResponseEntity.ok("Senha redefinida com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }
}
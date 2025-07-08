package br.com.itb.miniprojetospring.control;

import br.com.itb.miniprojetospring.model.Mensagem;
import br.com.itb.miniprojetospring.service.MensagemService;

import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/mensagens")
public class MensagemController {

    private final MensagemService mensagemService;

    public MensagemController(MensagemService mensagemService) {
        this.mensagemService = mensagemService;
    }

    @PostMapping
    public ResponseEntity<Mensagem> create(@Valid @RequestBody Mensagem mensagem) {
        return ResponseEntity.ok(mensagemService.save(mensagem));
    }

    @GetMapping
    public ResponseEntity<List<Mensagem>> findAll() {
        return ResponseEntity.ok(mensagemService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mensagem> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(mensagemService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mensagem> update(@PathVariable Integer id, @RequestBody Mensagem mensagem) {
        return ResponseEntity.ok(mensagemService.update(id, mensagem));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        mensagemService.delete(id);
        return ResponseEntity.noContent().build();
    }
}


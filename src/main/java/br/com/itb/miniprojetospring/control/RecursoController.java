package br.com.itb.miniprojetospring.control;

import br.com.itb.miniprojetospring.model.Recurso;
import br.com.itb.miniprojetospring.service.RecursoService;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/recursos")
public class RecursoController {

    private final RecursoService recursoService;

    public RecursoController(RecursoService recursoService) {
        this.recursoService = recursoService;
    }

    @PostMapping
    public ResponseEntity<Recurso> create(@RequestBody Recurso recurso) {
        return ResponseEntity.ok(recursoService.save(recurso));
    }

    @GetMapping
    public ResponseEntity<List<Recurso>> findAll() {
        return ResponseEntity.ok(recursoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recurso> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(recursoService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recurso> update(@PathVariable Integer id, @RequestBody Recurso recurso) {
        return ResponseEntity.ok(recursoService.update(id, recurso));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        recursoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

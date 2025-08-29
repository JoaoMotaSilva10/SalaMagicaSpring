package br.com.itb.miniprojetospring.control;

import br.com.itb.miniprojetospring.model.Reserva;
import br.com.itb.miniprojetospring.service.ReservaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/reservas")
public class ReservaController {

    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    // ---------------- CRUD ----------------

    @PostMapping
    public ResponseEntity<Reserva> create(@RequestBody Reserva reserva) {
        return ResponseEntity.ok(reservaService.save(reserva));
    }

    @GetMapping
    public ResponseEntity<List<Reserva>> findAll() {
        return ResponseEntity.ok(reservaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserva> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(reservaService.findById(id));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Reserva>> findByUsuarioId(@PathVariable Integer userId) {
        return ResponseEntity.ok(reservaService.findByUsuarioId(userId));
    }

    @GetMapping("/recurso/{recursoId}")
    public ResponseEntity<List<Reserva>> findByRecursoId(@PathVariable Integer recursoId) {
        return ResponseEntity.ok(reservaService.findByRecursoId(recursoId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reserva> update(@PathVariable Integer id, @RequestBody Reserva reserva) {
        return ResponseEntity.ok(reservaService.update(id, reserva));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        reservaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/confirmar")
    public ResponseEntity<Reserva> confirmarRealizacao(@PathVariable Integer id) {
        return ResponseEntity.ok(reservaService.confirmarRealizacao(id));
    }

    // ---------------- Estatísticas ----------------

    @GetMapping("/count")
    public ResponseEntity<Long> countAllReservas() {
        return ResponseEntity.ok(reservaService.countAll());
    }

    @GetMapping("/count/today")
    public ResponseEntity<Long> countReservasHoje() {
        return ResponseEntity.ok(reservaService.countReservasHoje());
    }

    @GetMapping("/count/pendentes")
    public ResponseEntity<Long> countReservasPendentes() {
        return ResponseEntity.ok(reservaService.countReservasPendentes());
    }

    @GetMapping("/count/marcadas")
    public ResponseEntity<Long> countReservasMarcadas() {
        return ResponseEntity.ok(reservaService.countReservasMarcadas());
    }

    @GetMapping("/count/realizadas")
    public ResponseEntity<Long> countReservasRealizadas() {
        return ResponseEntity.ok(reservaService.countReservasRealizadas());
    }
}

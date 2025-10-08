package br.com.itb.miniprojetospring.control;

import br.com.itb.miniprojetospring.dto.ReservaResponseDTO;
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
    public ResponseEntity<ReservaResponseDTO> create(@RequestBody Reserva reserva) {
        Reserva savedReserva = reservaService.save(reserva);
        return ResponseEntity.ok(reservaService.convertToDTO(savedReserva));
    }

    @GetMapping
    public ResponseEntity<List<ReservaResponseDTO>> findAll() {
        try {
            List<Reserva> reservas = reservaService.findAll();
            List<ReservaResponseDTO> dtos = reservas.stream()
                    .map(reservaService::convertToDTO)
                    .toList();
            return ResponseEntity.ok(dtos);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reserva> findById(@PathVariable Integer id) {
        return ResponseEntity.ok(reservaService.findById(id));
    }

    @GetMapping("/pessoa/{pessoaId}")
    public ResponseEntity<List<ReservaResponseDTO>> findByPessoaId(@PathVariable Long pessoaId) {
        try {
            return ResponseEntity.ok(reservaService.findByPessoaIdDTO(pessoaId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/recurso/{recursoId}")
    public ResponseEntity<List<Reserva>> findByRecursoId(@PathVariable Integer recursoId) {
        return ResponseEntity.ok(reservaService.findByRecursoId(recursoId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> update(@PathVariable Integer id, @RequestBody Reserva reserva) {
        Reserva updatedReserva = reservaService.update(id, reserva);
        return ResponseEntity.ok(reservaService.convertToDTO(updatedReserva));
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
        try {
            return ResponseEntity.ok(reservaService.countAll());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(0L);
        }
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

    @GetMapping("/debug")
    public ResponseEntity<?> debugReservas() {
        List<Reserva> reservas = reservaService.findAll();
        return ResponseEntity.ok(reservas);
    }
}

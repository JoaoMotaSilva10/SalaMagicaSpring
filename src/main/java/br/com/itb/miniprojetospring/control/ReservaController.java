package br.com.itb.miniprojetospring.control;

import br.com.itb.miniprojetospring.model.Reserva;
import br.com.itb.miniprojetospring.model.Equipamento;
import br.com.itb.miniprojetospring.model.ReservaRepository;
import br.com.itb.miniprojetospring.model.EquipamentoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private EquipamentoRepository equipamentoRepository;

    @GetMapping
    public List<Reserva> getAllReservas() {
        return reservaRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> criarReserva(@RequestBody Reserva reserva) {
        Equipamento equipamento = equipamentoRepository.findById(reserva.getEquipamento().getIdEquipamento())
                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado"));

        // Verifica se tem quantidade disponível
        if (equipamento.getQuantidade() <= 0) {
            return ResponseEntity.badRequest().body("Equipamento sem disponibilidade");
        }

        // Reduz quantidade
        equipamento.setQuantidade(Long.valueOf(equipamento.getQuantidade() - 1));
        equipamentoRepository.save(equipamento);

        Reserva novaReserva = reservaRepository.save(reserva);
        return ResponseEntity.ok(novaReserva);
    }
}

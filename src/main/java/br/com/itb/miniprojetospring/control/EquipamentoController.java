package br.com.itb.miniprojetospring.control;


import br.com.itb.miniprojetospring.model.Equipamento;
import br.com.itb.miniprojetospring.model.EquipamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/equipamentos")
public class EquipamentoController {

    @Autowired
    private EquipamentoRepository equipamentoRepository;

    @GetMapping
    public List<Equipamento> getAllEquipamentos() {
        return equipamentoRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<Equipamento> createEquipamento(@RequestBody Equipamento equipamento) {
        Equipamento savedEquipamento = equipamentoRepository.save(equipamento);
        return ResponseEntity.ok(savedEquipamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Equipamento> updateEquipamento(@PathVariable Long id, @RequestBody Equipamento equipamento) {
        Equipamento existingEquipamento = equipamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equipamento não encontrado"));

        existingEquipamento.setQuantidade(existingEquipamento.getQuantidade() + 1);
        Equipamento updatedEquipamento = equipamentoRepository.save(existingEquipamento);
        return ResponseEntity.ok(updatedEquipamento);
    }
}
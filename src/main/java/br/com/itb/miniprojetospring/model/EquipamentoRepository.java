package br.com.itb.miniprojetospring.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {
    Equipamento findByEquipamento(String equipamento);
}

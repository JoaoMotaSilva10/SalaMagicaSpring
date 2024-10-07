package br.com.itb.miniprojetospring.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "equipamento")
public class Equipamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEquipamento;

    @Column(nullable = false)
    private String equipamento;

    @Column(nullable = false)
    private Long quantidade;

    // Getters e Setters
    public Long getIdEquipamento() { return idEquipamento; }
    public void setIdEquipamento(Long idEquipamento) { this.idEquipamento = idEquipamento; }

    public String getEquipamento() { return equipamento; }
    public void setEquipamento(String equipamento) { this.equipamento = equipamento; }

    public Long getQuantidade() { return quantidade; }
    public void setQuantidade(Long quantidade) { this.quantidade = quantidade; }
}
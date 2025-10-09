package br.com.itb.miniprojetospring.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstatisticasDTO {
    private Long totalReservas;
    private Long reservasHoje;
    private Long reservasPendentes;
    private Long reservasMarcadas;
    private Long reservasRealizadas;
    private Long totalRecursos;
    private Long totalUsuarios;
    private Long totalAlunos;
    private Long totalAdministradores;
    private Long totalGerenciadores;
}
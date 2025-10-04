package br.com.itb.miniprojetospring.dto;

import br.com.itb.miniprojetospring.model.Recurso;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReservaResponseDTO {
    private Integer id;
    private String informacao;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataReservada;
    private String statusReserva;
    private Recurso recurso;
    
    // Dados da pessoa
    private Long pessoaId;
    private String pessoaNome;
    private String pessoaEmail;
    private String pessoaTipo;
}
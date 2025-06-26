package br.com.itb.miniprojetospring.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Reserva")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String informacao;

    private LocalDateTime dataCadastro;

    private LocalDateTime dataReservada;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "recurso_id")
    private Recurso recurso;

    private String statusReserva;

    // Getters e Setters
}


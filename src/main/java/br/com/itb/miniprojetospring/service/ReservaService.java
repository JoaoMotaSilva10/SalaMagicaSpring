package br.com.itb.miniprojetospring.service;

import br.com.itb.miniprojetospring.model.*;
import jakarta.persistence.*;
import org.hibernate.annotations.Table;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Service;


import java.util.List;

import jakarta.transaction.Transactional;

@Entity
@Table(name = "Reserva")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String informacao;

    private java.time.LocalDateTime dataCadastro;

    private java.time.LocalDateTime dataReservada;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "recurso_id", nullable = false)
    private Recurso recurso;

    private String statusReserva;

    // Getters e Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getInformacao() { return informacao; }
    public void setInformacao(String informacao) { this.informacao = informacao; }

    public java.time.LocalDateTime getDataCadastro() { return dataCadastro; }
    public void setDataCadastro(java.time.LocalDateTime dataCadastro) { this.dataCadastro = dataCadastro; }

    public java.time.LocalDateTime getDataReservada() { return dataReservada; }
    public void setDataReservada(java.time.LocalDateTime dataReservada) { this.dataReservada = dataReservada; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Recurso getRecurso() { return recurso; }
    public void setRecurso(Recurso recurso) { this.recurso = recurso; }

    public String getStatusReserva() { return statusReserva; }
    public void setStatusReserva(String statusReserva) { this.statusReserva = statusReserva; }
}

package br.com.itb.miniprojetospring.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Setter
@Getter
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true, nullable = false)
    private String email;

    private String senha;

    private String nivelAcesso; // ADMIN ou USER

    private ZonedDateTime dataCadastro;


    private String statusUsuario; // ATIVO, INATIVO, TROCAR_SENHA

    // Getters e Setters


    @PrePersist
    public void prePersist() {
        this.dataCadastro = ZonedDateTime.now();
    }
}

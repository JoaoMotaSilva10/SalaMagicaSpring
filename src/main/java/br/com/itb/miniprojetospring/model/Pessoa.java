package br.com.itb.miniprojetospring.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Setter
@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(unique = true, nullable = false)
    private String email;

    private String senha;

    private ZonedDateTime dataCadastro;

    private String statusUsuario; // ATIVO, INATIVO, TROCAR_SENHA

    @PrePersist
    public void prePersist() {
        if (this.dataCadastro == null) {
            this.dataCadastro = ZonedDateTime.now();
        }
        if (this.statusUsuario == null) {
            this.statusUsuario = "ATIVO";
        }
    }

    public abstract String getTipoUsuario();
}
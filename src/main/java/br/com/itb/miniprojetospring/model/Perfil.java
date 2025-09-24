package br.com.itb.miniprojetospring.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "usuario_id", unique = true)
    private Usuario usuario;

    private String rm;
    private String unidade;
    private String turma;
    private String serie;
    private String periodo;
    private String cpf;

    public Perfil() {}

    public Perfil(Usuario usuario) {
        this.usuario = usuario;
    }
}
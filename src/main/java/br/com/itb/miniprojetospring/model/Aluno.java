package br.com.itb.miniprojetospring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Aluno extends Pessoa {

    private String rm;
    private String turma;
    private String serie;
    private String periodo;
    private String cpf;

    @Transient
    @JsonProperty("tipoUsuario")
    private String tipoUsuario;

    @Override
    public String getTipoUsuario() {
        return "ALUNO";
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
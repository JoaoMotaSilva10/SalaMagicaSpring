package br.com.itb.miniprojetospring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Gerenciador extends Pessoa {

    private String unidade;
    private String departamento;
    private String cargo;

    @Transient
    @JsonProperty("tipoUsuario")
    private String tipoUsuario;

    @Override
    public String getTipoUsuario() {
        return "GERENCIADOR";
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
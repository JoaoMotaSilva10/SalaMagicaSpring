package br.com.itb.miniprojetospring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Administrador extends Pessoa {

    private String nivelPermissao; // SUPER_ADMIN, ADMIN
    private String setor;

    @Transient
    @JsonProperty("tipoUsuario")
    private String tipoUsuario;

    @Override
    public String getTipoUsuario() {
        return "ADMINISTRADOR";
    }

    public void setTipoUsuario(String tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
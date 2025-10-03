package br.com.itb.miniprojetospring.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Administrador extends Pessoa {

    private String nivelPermissao; // SUPER_ADMIN, ADMIN
    private String setor;

    @Override
    public String getTipoUsuario() {
        return "ADMINISTRADOR";
    }
}
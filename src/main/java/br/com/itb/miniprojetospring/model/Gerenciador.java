package br.com.itb.miniprojetospring.model;

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

    @Override
    public String getTipoUsuario() {
        return "GERENCIADOR";
    }
}
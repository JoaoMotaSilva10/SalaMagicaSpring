package br.com.itb.miniprojetospring.model;

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

    @Override
    public String getTipoUsuario() {
        return "ALUNO";
    }
}
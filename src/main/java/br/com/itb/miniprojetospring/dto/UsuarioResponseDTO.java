package br.com.itb.miniprojetospring.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class UsuarioResponseDTO {
    private Long id;
    private String nome;
    private String email;
    private ZonedDateTime dataCadastro;
    private String statusUsuario;
    private String tipoUsuario;
    
    // Campos específicos do Aluno
    private String rm;
    private String turma;
    private String serie;
    private String periodo;
    private String cpf;
    private String unidade;
    
    // Campos específicos do Administrador
    private String nivelPermissao;
    private String setor;
    
    // Campos específicos do Gerenciador
    private String departamento;
    private String cargo;
}
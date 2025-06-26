package br.com.itb.miniprojetospring.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.hibernate.annotations.Table;
import org.springframework.data.annotation.Id;

@Entity
@Table(name = "Recurso")
public class Recurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    private String descricao;

    private String tipo;

    private String statusRecurso;

    // Getters e Setters

}

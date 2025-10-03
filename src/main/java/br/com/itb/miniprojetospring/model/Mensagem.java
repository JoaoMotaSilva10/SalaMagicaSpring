package br.com.itb.miniprojetospring.model;


import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

@Entity
@Table(name = "Mensagem")
public class Mensagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime dataMensagem;


    @NotBlank(message = "O emissor é obrigatório")
    private String emissor;

    @NotBlank(message = "O email é obrigatório")
    private String email;

    private String rm;

    private String assunto;

    @NotBlank(message = "O texto é obrigatório")
    private String texto;

    @NotBlank(message = "O status da mensagem é obrigatório")
    private String statusMensagem;

    // Getters e Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getDataMensagem() {
        return dataMensagem;
    }

    public void setDataMensagem(LocalDateTime dataMensagem) {
        this.dataMensagem = dataMensagem;
    }

    public String getEmissor() {
        return emissor;
    }

    public void setEmissor(String emissor) {
        this.emissor = emissor;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRm() {
        return rm;
    }

    public void setRm(String rm) {
        this.rm = rm;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getStatusMensagem() {
        return statusMensagem;
    }

    public void setStatusMensagem(String statusMensagem) {
        this.statusMensagem = statusMensagem;
    }
}

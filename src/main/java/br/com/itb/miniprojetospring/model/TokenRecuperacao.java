package br.com.itb.miniprojetospring.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
public class TokenRecuperacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private LocalDateTime dataExpiracao;

    private boolean usado;

    @PrePersist
    public void prePersist() {
        if (this.dataExpiracao == null) {
            this.dataExpiracao = LocalDateTime.now().plusHours(1); // Token válido por 1 hora
        }
        this.usado = false;
    }
}
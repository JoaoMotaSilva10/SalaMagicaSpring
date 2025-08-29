package br.com.itb.miniprojetospring.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResetSenhaRequest {
    private String email;
    private String novaSenha;

    public ResetSenhaRequest() {}

    public ResetSenhaRequest(String email, String novaSenha) {
        this.email = email;
        this.novaSenha = novaSenha;
    }

}

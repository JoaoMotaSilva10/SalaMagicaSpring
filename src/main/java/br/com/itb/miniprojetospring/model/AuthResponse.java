package br.com.itb.miniprojetospring.model;

public class AuthResponse {

    private Integer id;
    private String email;
    private String nivelAcesso;

    public AuthResponse(Integer id, String email, String nivelAcesso) {
        this.id = id;
        this.email = email;
        this.nivelAcesso = nivelAcesso;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNivelAcesso() { return nivelAcesso; }
    public void setNivelAcesso(String nivelAcesso) { this.nivelAcesso = nivelAcesso; }
}

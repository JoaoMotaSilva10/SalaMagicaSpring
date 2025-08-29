package br.com.itb.miniprojetospring.service;


import br.com.itb.miniprojetospring.model.PasswordResetToken;
import br.com.itb.miniprojetospring.model.PasswordResetTokenRepository;
import br.com.itb.miniprojetospring.model.Usuario;
import br.com.itb.miniprojetospring.model.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class PasswordResetService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordResetTokenRepository tokenRepository;

    public PasswordResetService(UsuarioRepository usuarioRepository, PasswordResetTokenRepository tokenRepository) {
        this.usuarioRepository = usuarioRepository;
        this.tokenRepository = tokenRepository;
    }

    public String gerarToken(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToken = new PasswordResetToken();
        resetToken.setToken(token);
        resetToken.setUsuario(usuario);
        resetToken.setExpiryDate(LocalDateTime.now().plusHours(1));

        tokenRepository.save(resetToken);

        // Aqui você dispararia um e-mail com o link
        // Exemplo: http://localhost:3000/reset-senha?token=" + token

        return token;
    }

    public void redefinirSenha(String token, String novaSenha) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token);

        if (resetToken == null || resetToken.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token inválido ou expirado");
        }

        Usuario usuario = resetToken.getUsuario();
        usuario.setSenha(novaSenha); // de preferência criptografar (BCrypt)
        usuarioRepository.save(usuario);

        tokenRepository.delete(resetToken); // invalida token
    }
}


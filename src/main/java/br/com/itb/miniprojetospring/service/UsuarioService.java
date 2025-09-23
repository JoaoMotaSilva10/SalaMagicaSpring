package br.com.itb.miniprojetospring.service;

import br.com.itb.miniprojetospring.model.TokenRecuperacao;
import br.com.itb.miniprojetospring.model.TokenRecuperacaoRepository;
import br.com.itb.miniprojetospring.model.Usuario;
import br.com.itb.miniprojetospring.model.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Random;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final TokenRecuperacaoRepository tokenRepository;
    private final EmailService emailService;

    public UsuarioService(UsuarioRepository usuarioRepository, 
                         TokenRecuperacaoRepository tokenRepository,
                         EmailService emailService) {
        this.usuarioRepository = usuarioRepository;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
    }

    public Usuario save(Usuario usuario) {
        String senhaBase64 = Base64.getEncoder().encodeToString(usuario.getSenha().getBytes());
        usuario.setSenha(senhaBase64);
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email).orElse(null);
    }

    public Usuario update(Long id, Usuario usuario) {
        return usuarioRepository.findById(id)
                .map(u -> {
                    u.setNome(usuario.getNome());
                    u.setEmail(usuario.getEmail());
                    u.setSenha(Base64.getEncoder().encodeToString(usuario.getSenha().getBytes()));
                    u.setNivelAcesso(usuario.getNivelAcesso());
                    if (usuario.getStatusUsuario() != null) {
                        u.setStatusUsuario(usuario.getStatusUsuario());
                    }
                    return usuarioRepository.save(u);
                })
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }

    @Transactional
    public void solicitarRecuperacaoSenha(String email) {
        Usuario usuario = findByEmail(email);
        if (usuario == null) {
            throw new RuntimeException("Email não encontrado");
        }

        // Remove tokens antigos do usuário
        tokenRepository.deleteByUsuario(usuario);

        // Gera novo token de 6 dígitos
        String token = String.format("%06d", new Random().nextInt(999999));

        // Salva token no banco
        TokenRecuperacao tokenRecuperacao = new TokenRecuperacao();
        tokenRecuperacao.setToken(token);
        tokenRecuperacao.setUsuario(usuario);
        tokenRepository.save(tokenRecuperacao);

        // Envia email real
        emailService.enviarEmailRecuperacao(email, token);
    }

    @Transactional
    public void redefinirSenha(String token, String novaSenha) {
        TokenRecuperacao tokenRecuperacao = tokenRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Token inválido"));

        if (tokenRecuperacao.isUsado()) {
            throw new RuntimeException("Token já foi utilizado");
        }

        if (tokenRecuperacao.getDataExpiracao().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expirado");
        }

        // Atualiza senha do usuário
        Usuario usuario = tokenRecuperacao.getUsuario();
        String senhaBase64 = Base64.getEncoder().encodeToString(novaSenha.getBytes());
        usuario.setSenha(senhaBase64);
        usuarioRepository.save(usuario);

        // Marca token como usado
        tokenRecuperacao.setUsado(true);
        tokenRepository.save(tokenRecuperacao);
    }
}
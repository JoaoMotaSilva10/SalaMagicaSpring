package br.com.itb.miniprojetospring.service;

import br.com.itb.miniprojetospring.model.Usuario;
import br.com.itb.miniprojetospring.model.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public Usuario save(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> findById(Integer id) {
        return usuarioRepository.findById(id);
    }

    @Transactional
    public Usuario update(Integer id, Usuario usuario) {
        Usuario existing = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario não encontrado"));
        existing.setNome(usuario.getNome());
        existing.setEmail(usuario.getEmail());
        existing.setSenha(usuario.getSenha());
        existing.setNivelAcesso(usuario.getNivelAcesso());
        existing.setFoto(usuario.getFoto());
        existing.setDataCadastro(usuario.getDataCadastro());
        existing.setStatusUsuario(usuario.getStatusUsuario());
        return usuarioRepository.save(existing);
    }

    @Transactional
    public void delete(Integer id) {
        usuarioRepository.deleteById(id);
    }

    public Optional<Usuario> authenticate(String email, String senha) {
        Optional<Usuario> usuario = usuarioRepository.findByEmail(email);
        return usuario.filter(u -> u.getSenha().equals(senha));
    }
}

package br.com.itb.miniprojetospring.service;

import br.com.itb.miniprojetospring.model.Perfil;
import br.com.itb.miniprojetospring.model.PerfilRepository;
import br.com.itb.miniprojetospring.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PerfilService {

    @Autowired
    private PerfilRepository perfilRepository;

    public Perfil buscarPorUsuarioEmail(String email) {
        return perfilRepository.findByUsuarioEmail(email).orElse(null);
    }

    public Perfil criarPerfil(Usuario usuario) {
        Perfil perfil = new Perfil(usuario);
        return perfilRepository.save(perfil);
    }

    public Perfil atualizarPerfil(Perfil perfil) {
        return perfilRepository.save(perfil);
    }
}
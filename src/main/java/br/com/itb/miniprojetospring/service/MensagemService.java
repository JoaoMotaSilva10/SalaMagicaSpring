package br.com.itb.miniprojetospring.service;

import br.com.itb.miniprojetospring.model.*;
import br.com.itb.miniprojetospring.model.MensagemRepository;

import org.springframework.stereotype.Service;

import java.util.List;

import jakarta.transaction.Transactional;

@Service
public class MensagemService {

    private final MensagemRepository mensagemRepository;

    public MensagemService(MensagemRepository mensagemRepository) {
        this.mensagemRepository = mensagemRepository;
    }

    @Transactional
    public Mensagem save(Mensagem mensagem) {
        return mensagemRepository.save(mensagem);
    }

    public List<Mensagem> findAll() {
        return mensagemRepository.findAll();
    }

    public Mensagem findById(Integer id) {
        return mensagemRepository.findById(id).orElseThrow(() -> new RuntimeException("Mensagem não encontrada"));
    }

    @Transactional
    public Mensagem update(Integer id, Mensagem mensagem) {
        Mensagem existing = mensagemRepository.findById(id).orElseThrow(() -> new RuntimeException("Mensagem não encontrada"));
        existing.setDataMensagem(mensagem.getDataMensagem());
        existing.setEmissor(mensagem.getEmissor());
        existing.setEmail(mensagem.getEmail());
        existing.setTelefone(mensagem.getTelefone());
        existing.setTexto(mensagem.getTexto());
        existing.setStatusMensagem(mensagem.getStatusMensagem());
        return mensagemRepository.save(existing);
    }

    @Transactional
    public void delete(Integer id) {
        mensagemRepository.deleteById(id);
    }
}

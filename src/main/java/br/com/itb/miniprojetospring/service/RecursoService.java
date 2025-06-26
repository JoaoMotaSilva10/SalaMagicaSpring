package br.com.itb.miniprojetospring.service;

import br.com.itb.miniprojetospring.model.Recurso;
import br.com.itb.miniprojetospring.model.RecursoRepository;

import org.springframework.stereotype.Service;

import java.util.List;

import jakarta.transaction.Transactional;

@Service
public class RecursoService {

    private final RecursoRepository recursoRepository;

    public RecursoService(RecursoRepository recursoRepository) {
        this.recursoRepository = recursoRepository;
    }

    @Transactional
    public Recurso save(Recurso recurso) {
        return recursoRepository.save(recurso);
    }

    public List<Recurso> findAll() {
        return recursoRepository.findAll();
    }

    public Recurso findById(Integer id) {
        return recursoRepository.findById(id).orElseThrow(() -> new RuntimeException("Recurso não encontrado"));
    }

    @Transactional
    public Recurso update(Integer id, Recurso recurso) {
        Recurso existing = recursoRepository.findById(id).orElseThrow(() -> new RuntimeException("Recurso não encontrado"));
        existing.setNome(recurso.getNome());
        existing.setDescricao(recurso.getDescricao());
        existing.setTipo(recurso.getTipo());
        existing.setStatusRecurso(recurso.getStatusRecurso());
        return recursoRepository.save(existing);
    }

    @Transactional
    public void delete(Integer id) {
        recursoRepository.deleteById(id);
    }
}

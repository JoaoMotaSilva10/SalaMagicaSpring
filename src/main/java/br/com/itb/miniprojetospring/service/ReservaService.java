package br.com.itb.miniprojetospring.service;

import br.com.itb.miniprojetospring.model.*;
import org.springframework.stereotype.Service;

import java.util.List;

import jakarta.transaction.Transactional;

@Service
public class ReservaService {

    private final ReservaRepository reservaRepository;

    public ReservaService(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    @Transactional
    public Reserva save(Reserva reserva) {
        return reservaRepository.save(reserva);
    }

    public List<Reserva> findAll() {
        return reservaRepository.findAll();
    }

    public List<Reserva> findByUsuarioId(Integer usuarioId) {
        return reservaRepository.findByUsuarioId(usuarioId);
    }

    public List<Reserva> findByRecursoId(Integer recursoId) {
        return reservaRepository.findByRecursoId(recursoId);
    }

    public Reserva findById(Integer id) {
        return reservaRepository.findById(id).orElseThrow(() -> new RuntimeException("Reserva não encontrada"));
    }

    @Transactional
    public Reserva update(Integer id, Reserva reserva) {
        Reserva existing = reservaRepository.findById(id).orElseThrow(() -> new RuntimeException("Reserva não encontrada"));
        existing.setInformacao(reserva.getInformacao());
        existing.setDataCadastro(reserva.getDataCadastro());
        existing.setDataReservada(reserva.getDataReservada());
        existing.setUsuario(reserva.getUsuario());
        existing.setRecurso(reserva.getRecurso());
        existing.setStatusReserva(reserva.getStatusReserva());
        return reservaRepository.save(existing);
    }

    @Transactional
    public void delete(Integer id) {
        reservaRepository.deleteById(id);
    }
}

package br.com.itb.miniprojetospring.service;

import br.com.itb.miniprojetospring.model.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    public Long countAll() {
        return reservaRepository.count();
    }

    public Long countReservasHoje() {
        LocalDate hoje = LocalDate.now();
        LocalDateTime inicio = hoje.atStartOfDay();
        LocalDateTime fim = hoje.atTime(LocalTime.MAX);

        return reservaRepository.countByDataReservadaHoje(inicio, fim);
    }


    public Long countReservasPendentes() {
        // seus dados usam ATIVO como "a analisar"
        return reservaRepository.countByStatusReserva("ATIVO");
    }

    public Long countReservasMarcadas() {
        // se quiser só futuras: return reservaRepository.countByStatusReservaAndDataReservadaAfter("ACEITA", LocalDateTime.now());
        return reservaRepository.countByStatusReserva("ACEITA");
    }

    public Long countReservasRealizadas() {
        return reservaRepository.countByStatusReserva("REALIZADA");
    }

    @Transactional
    public Reserva confirmarRealizacao(Integer id) {
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva não encontrada"));

        reserva.setStatusReserva("REALIZADA");
        return reservaRepository.save(reserva);
    }

}
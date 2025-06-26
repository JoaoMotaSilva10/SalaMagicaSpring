package br.com.itb.miniprojetospring.model;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    List<Reserva> findByUsuarioId(Integer usuarioId);
    List<Reserva> findByRecursoId(Integer recursoId);
}

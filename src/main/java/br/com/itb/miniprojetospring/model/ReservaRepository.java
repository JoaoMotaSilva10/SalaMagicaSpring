package br.com.itb.miniprojetospring.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    List<Reserva> findByUsuarioId(Integer usuarioId);
    List<Reserva> findByRecursoId(Integer recursoId);

    long count();
    long countByStatusReserva(String statusReserva);

    @Query("SELECT COUNT(r) FROM Reserva r WHERE r.dataReservada BETWEEN :inicio AND :fim")
    long countByDataReservadaHoje(@Param("inicio") LocalDateTime inicio,
                                  @Param("fim") LocalDateTime fim);

    // (opcional) se quiser "marcadas" só no futuro:
    long countByStatusReservaAndDataReservadaAfter(String statusReserva, LocalDateTime data);
}

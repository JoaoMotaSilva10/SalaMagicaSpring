package br.com.itb.miniprojetospring.control;

import br.com.itb.miniprojetospring.dto.EstatisticasDTO;
import br.com.itb.miniprojetospring.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/estatisticas")
@CrossOrigin(origins = "*")
public class EstatisticasController {

    private final ReservaService reservaService;
    private final RecursoService recursoService;
    private final AlunoService alunoService;
    private final AdministradorService administradorService;
    private final GerenciadorService gerenciadorService;

    public EstatisticasController(ReservaService reservaService, 
                                 RecursoService recursoService,
                                 AlunoService alunoService,
                                 AdministradorService administradorService,
                                 GerenciadorService gerenciadorService) {
        this.reservaService = reservaService;
        this.recursoService = recursoService;
        this.alunoService = alunoService;
        this.administradorService = administradorService;
        this.gerenciadorService = gerenciadorService;
    }

    @GetMapping
    public ResponseEntity<EstatisticasDTO> getEstatisticasCompletas() {
        try {
            EstatisticasDTO stats = new EstatisticasDTO();
            
            // Estatísticas de reservas
            stats.setTotalReservas(reservaService.countAll());
            stats.setReservasHoje(reservaService.countReservasHoje());
            stats.setReservasPendentes(reservaService.countReservasPendentes());
            stats.setReservasMarcadas(reservaService.countReservasMarcadas());
            stats.setReservasRealizadas(reservaService.countReservasRealizadas());
            
            // Estatísticas de recursos
            stats.setTotalRecursos((long) recursoService.findAll().size());
            
            // Estatísticas de usuários
            stats.setTotalAlunos((long) alunoService.findAll().size());
            stats.setTotalAdministradores((long) administradorService.findAll().size());
            stats.setTotalGerenciadores((long) gerenciadorService.findAll().size());
            stats.setTotalUsuarios(stats.getTotalAlunos() + stats.getTotalAdministradores() + stats.getTotalGerenciadores());
            
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
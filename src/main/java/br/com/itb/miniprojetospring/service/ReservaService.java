package br.com.itb.miniprojetospring.service;

import br.com.itb.miniprojetospring.dto.ReservaResponseDTO;
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
    private final PessoaRepository pessoaRepository;

    public ReservaService(ReservaRepository reservaRepository, PessoaRepository pessoaRepository) {
        this.reservaRepository = reservaRepository;
        this.pessoaRepository = pessoaRepository;
    }

    @Transactional
    public Reserva save(Reserva reserva) {
        // Se pessoaId foi enviado no JSON, busca a pessoa
        if (reserva.getPessoaId() != null && reserva.getPessoa() == null) {
            Pessoa pessoa = buscarPessoaPorId(reserva.getPessoaId());
            reserva.setPessoa(pessoa);
        }
        
        // Se pessoa não foi definida, lança erro
        if (reserva.getPessoa() == null) {
            throw new RuntimeException("pessoaId é obrigatório para criar uma reserva");
        }
        
        return reservaRepository.save(reserva);
    }
    
    private Pessoa buscarPessoaPorId(Long id) {
        return pessoaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pessoa não encontrada com ID: " + id));
    }

    public List<Reserva> findAll() {
        return reservaRepository.findAll();
    }

    public List<Reserva> findByPessoaId(Long pessoaId) {
        return reservaRepository.findByPessoaId(pessoaId);
    }
    
    public List<ReservaResponseDTO> findByPessoaIdDTO(Long pessoaId) {
        return reservaRepository.findByPessoaId(pessoaId).stream()
                .map(this::convertToDTO)
                .toList();
    }
    
    public ReservaResponseDTO convertToDTO(Reserva reserva) {
        ReservaResponseDTO dto = new ReservaResponseDTO();
        dto.setId(reserva.getId());
        dto.setInformacao(reserva.getInformacao());
        dto.setDataCadastro(reserva.getDataCadastro());
        dto.setDataReservada(reserva.getDataReservada());
        dto.setStatusReserva(reserva.getStatusReserva());
        dto.setRecurso(reserva.getRecurso());
        
        // Se a pessoa já está carregada, usa ela
        if (reserva.getPessoa() != null) {
            dto.setPessoaId(reserva.getPessoa().getId());
            dto.setPessoaNome(reserva.getPessoa().getNome());
            dto.setPessoaEmail(reserva.getPessoa().getEmail());
            dto.setPessoaTipo(reserva.getPessoa().getTipoUsuario());
        } else {
            // Se não está carregada, tenta buscar (com tratamento de erro)
            try {
                Reserva fullReserva = reservaRepository.findById(reserva.getId()).orElse(null);
                if (fullReserva != null && fullReserva.getPessoa() != null) {
                    dto.setPessoaId(fullReserva.getPessoa().getId());
                    dto.setPessoaNome(fullReserva.getPessoa().getNome());
                    dto.setPessoaEmail(fullReserva.getPessoa().getEmail());
                    dto.setPessoaTipo(fullReserva.getPessoa().getTipoUsuario());
                }
            } catch (Exception e) {
                // Se der erro, deixa os campos da pessoa como null
                System.err.println("Erro ao buscar pessoa da reserva: " + e.getMessage());
            }
        }
        
        return dto;
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
        existing.setRecurso(reserva.getRecurso());
        existing.setStatusReserva(reserva.getStatusReserva());
        
        // Se pessoaId foi enviado, atualiza a pessoa
        if (reserva.getPessoaId() != null) {
            Pessoa pessoa = buscarPessoaPorId(reserva.getPessoaId());
            existing.setPessoa(pessoa);
        }
        
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
        return reservaRepository.countByDataReservadaHoje(LocalDate.now());
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
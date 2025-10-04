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
        System.out.println("=== DEBUG RESERVA SAVE ===");
        System.out.println("pessoaId recebido: " + reserva.getPessoaId());
        System.out.println("pessoa atual: " + reserva.getPessoa());
        
        // Se pessoaId foi enviado no JSON, busca a pessoa
        if (reserva.getPessoaId() != null && reserva.getPessoa() == null) {
            System.out.println("Buscando pessoa com ID: " + reserva.getPessoaId());
            Pessoa pessoa = buscarPessoaPorId(reserva.getPessoaId());
            System.out.println("Pessoa encontrada: " + pessoa.getNome() + " (ID: " + pessoa.getId() + ")");
            reserva.setPessoa(pessoa);
        }
        
        // Se pessoa não foi definida, lança erro
        if (reserva.getPessoa() == null) {
            throw new RuntimeException("pessoaId é obrigatório para criar uma reserva");
        }
        
        System.out.println("Salvando reserva com pessoa: " + reserva.getPessoa().getNome());
        Reserva saved = reservaRepository.save(reserva);
        System.out.println("Reserva salva com ID: " + saved.getId());
        System.out.println("===========================");
        
        return saved;
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
        
        if (reserva.getPessoa() != null) {
            dto.setPessoaId(reserva.getPessoa().getId());
            dto.setPessoaNome(reserva.getPessoa().getNome());
            dto.setPessoaEmail(reserva.getPessoa().getEmail());
            dto.setPessoaTipo(reserva.getPessoa().getTipoUsuario());
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
        existing.setPessoa(reserva.getPessoa());
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
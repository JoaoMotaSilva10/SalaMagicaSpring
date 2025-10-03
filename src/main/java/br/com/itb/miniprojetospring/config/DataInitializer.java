package br.com.itb.miniprojetospring.config;

import br.com.itb.miniprojetospring.model.*;
import br.com.itb.miniprojetospring.service.RecursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AdministradorRepository administradorRepository;
    
    @Autowired
    private GerenciadorRepository gerenciadorRepository;
    
    @Autowired
    private AlunoRepository alunoRepository;
    
    @Autowired
    private RecursoService recursoService;
    
    @Autowired
    private ReservaRepository reservaRepository;

    @Override
    public void run(String... args) throws Exception {
        // Inserir administradores apenas se não existirem
        if (administradorRepository.findAll().isEmpty()) {
            
            Administrador admin = new Administrador();
            admin.setNome("João Mota");
            admin.setEmail("joaopedromotasilva200@gmail.com");
            admin.setSenha("MTIzNDU2Nzg=");
            admin.setNivelPermissao("SUPER_ADMIN");
            admin.setSetor("TI");
            admin.setStatusUsuario("ATIVO");
            administradorRepository.save(admin);

            Gerenciador gerenciador = new Gerenciador();
            gerenciador.setNome("Maria Santos");
            gerenciador.setEmail("maria@escola.com");
            gerenciador.setSenha("MTIzNDU2Nzg=");
            gerenciador.setUnidade("Campus Principal");
            gerenciador.setDepartamento("Coordenação");
            gerenciador.setCargo("Coordenadora");
            gerenciador.setStatusUsuario("ATIVO");
            gerenciadorRepository.save(gerenciador);
            
            Aluno aluno = new Aluno();
            aluno.setNome("Pedro Silva");
            aluno.setEmail("pedro@aluno.com");
            aluno.setSenha("MTIzNDU2Nzg=");
            aluno.setRm("12345");
            aluno.setTurma("3A");
            aluno.setSerie("3º Ano");
            aluno.setPeriodo("Manhã");
            aluno.setCpf("123.456.789-00");
            aluno.setStatusUsuario("ATIVO");
            alunoRepository.save(aluno);
        }

        // Inserir recursos apenas se não existirem
        if (recursoService.findAll().isEmpty()) {
            
            Recurso auditorio = new Recurso();
            auditorio.setNome("Auditório");
            auditorio.setDescricao("Auditório principal do campus");
            auditorio.setTipo("AMBIENTE");
            auditorio.setStatusRecurso("ATIVO");
            recursoService.save(auditorio);

            Recurso sala = new Recurso();
            sala.setNome("Sala de Aula");
            sala.setDescricao("Sala padrão para turmas regulares");
            sala.setTipo("AMBIENTE");
            sala.setStatusRecurso("ATIVO");
            recursoService.save(sala);

            Recurso labInfo = new Recurso();
            labInfo.setNome("Laboratório de Informática");
            labInfo.setDescricao("Espaço com computadores para aulas práticas");
            labInfo.setTipo("AMBIENTE");
            labInfo.setStatusRecurso("ATIVO");
            recursoService.save(labInfo);

            Recurso computador = new Recurso();
            computador.setNome("Computador");
            computador.setDescricao("Computador desktop padrão");
            computador.setTipo("EQUIPAMENTO");
            computador.setStatusRecurso("ATIVO");
            recursoService.save(computador);

            Recurso datashow = new Recurso();
            datashow.setNome("Datashow");
            datashow.setDescricao("Projetor multimídia portátil");
            datashow.setTipo("EQUIPAMENTO");
            datashow.setStatusRecurso("ATIVO");
            recursoService.save(datashow);
        }
        
        // Inserir reservas de exemplo apenas se não existirem
        if (reservaRepository.findAll().isEmpty()) {
            Aluno aluno = alunoRepository.findAll().get(0);
            Recurso auditorio = recursoService.findAll().get(0);
            
            Reserva reserva1 = new Reserva();
            reserva1.setInformacao("Apresentação do projeto final");
            reserva1.setDataReservada(java.time.LocalDateTime.now().plusDays(2));
            reserva1.setPessoa(aluno);
            reserva1.setRecurso(auditorio);
            reserva1.setStatusReserva("EM_ANALISE");
            reservaRepository.save(reserva1);
            
            Gerenciador gerenciador = gerenciadorRepository.findAll().get(0);
            Recurso sala = recursoService.findAll().get(1);
            
            Reserva reserva2 = new Reserva();
            reserva2.setInformacao("Reunião de coordenação");
            reserva2.setDataReservada(java.time.LocalDateTime.now().plusDays(1));
            reserva2.setPessoa(gerenciador);
            reserva2.setRecurso(sala);
            reserva2.setStatusReserva("EM_ANALISE");
            reservaRepository.save(reserva2);
        }
    }
}
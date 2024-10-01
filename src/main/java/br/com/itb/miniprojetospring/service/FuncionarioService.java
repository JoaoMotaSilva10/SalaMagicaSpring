package br.com.itb.miniprojetospring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import br.com.itb.miniprojetospring.model.Funcionario;
import br.com.itb.miniprojetospring.model.FuncionarioRepository;

@Service
public class FuncionarioService {
    // Criar objeto repository
    final FuncionarioRepository funcionarioRepository;

    // Injeção de Dependência
    public FuncionarioService(FuncionarioRepository _funcionarioRepository) {
        this.funcionarioRepository = _funcionarioRepository;
    }

    // Método INSERT INTO FUNCIONARIO
    @Transactional
    public Funcionario save(Funcionario _funcionario) {
        return funcionarioRepository.save(_funcionario);
    }

    // Método SELECT * FROM FUNCIONARIO
    public List<Funcionario> findAll() {
        return funcionarioRepository.findAll();
    }

    public Funcionario findAllById(long id) {
        return funcionarioRepository.findById(id).orElse(null); // Use findById para retornar um Optional
    }

    // Método UPDATE FUNCIONARIO
    @Transactional
    public Funcionario update(Long id, Funcionario funcionario) {
        Optional<Funcionario> optionalFuncionario = funcionarioRepository.findById(id);

        if (optionalFuncionario.isPresent()) {
            Funcionario existingFuncionario = optionalFuncionario.get();
            // Atualiza os campos necessários
            existingFuncionario.setEmail(funcionario.getEmail());
            existingFuncionario.setNome(funcionario.getNome());
            existingFuncionario.setSenha(funcionario.getSenha());

            // Salva as alterações no banco de dados
            return funcionarioRepository.save(existingFuncionario);
        } else {
            throw new RuntimeException("Funcionário não encontrado com o ID: " + id);
        }
    }

    // Método DELETE FUNCIONARIO
    @Transactional
    public void delete(long id) {
        funcionarioRepository.deleteById(id);
    }

}

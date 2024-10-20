package br.com.itb.miniprojetospring.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import br.com.itb.miniprojetospring.model.Funcionario;
import br.com.itb.miniprojetospring.model.FuncionarioRepository;

@Service
public class FuncionarioService {

    // Injeção de Dependência do repositório
    final FuncionarioRepository funcionarioRepository;

    public FuncionarioService(FuncionarioRepository funcionarioRepository) {
        this.funcionarioRepository = funcionarioRepository;
    }

    // Método INSERT INTO FUNCIONARIO
    @Transactional
    public Funcionario save(Funcionario funcionario) {
        return funcionarioRepository.save(funcionario);
    }

    // Método SELECT * FROM FUNCIONARIO
    public List<Funcionario> findAll() {
        return funcionarioRepository.findAll();
    }

    // Método para buscar funcionário por ID
    public Funcionario findAllById(long id) {
        return funcionarioRepository.findById(id).orElse(null); // Retorna um funcionário ou null
    }

    // Método UPDATE FUNCIONARIO
    @Transactional
    public Funcionario update(Long id, Funcionario funcionario) {
        Optional<Funcionario> optionalFuncionario = funcionarioRepository.findById(id);

        if (optionalFuncionario.isPresent()) {
            Funcionario existingFuncionario = optionalFuncionario.get();
            // Atualiza os campos do funcionário
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

    // Método para login (autenticação)
    public Optional<Funcionario> authenticate(String email, String senha) {
        Optional<Funcionario> funcionarioOptional = funcionarioRepository.findByEmail(email);
        if (funcionarioOptional.isPresent()) {
            Funcionario funcionario = funcionarioOptional.get();
            // Verifica se a senha está correta
            if (funcionario.getSenha().equals(senha)) {
                return Optional.of(funcionario); // Retorna o funcionário autenticado
            }
        }
        return Optional.empty(); // Retorna vazio se a autenticação falhar
    }
}

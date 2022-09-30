package com.letscode.alunos.service;

import com.letscode.alunos.entity.Aluno;
import com.letscode.alunos.repository.AlunoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * O Spring percebe que temos a classe anotada com @Service, e irá instanciar essa classe e registrá-la em seu ApplicationContext
 * tornando nossa classe um Bean Spring-managed.
 */
@Service
public class AlunoServiceImpl implements AlunoService {

    private final AlunoRepository alunoRepository;

    public AlunoServiceImpl(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    @Override
    public Aluno salvar(Aluno aluno) {
        return alunoRepository.save(aluno);
    }

    @Override
    public Aluno buscaPorId(Long id) throws Exception {
        var aluno =  alunoRepository.findById(id);

        if (aluno.isEmpty()) {
            throw new Exception("Aluno não foi encontrado");
        }

        return aluno.get();
    }

    @Override
    public List<Aluno> buscaTodos() {
        return alunoRepository.findAll();
    }

    @Override
    public Aluno alterarAluno(Long id, String nome) throws Exception {
        Aluno aluno = buscaPorId(id);
        aluno.setNome(nome);
        alunoRepository.save(aluno);
        return aluno;
    }

    @Override
    public String delete(Long id) throws Exception {
        alunoRepository.deleteById(buscaPorId(id).getId());
        return "Aluno deletado";
    }

    @Override
    public List<Aluno> buscaPorNome(String nome) {
        return alunoRepository.findByNome(nome);
    }

    @Override
    public List<Aluno> buscaPorIdade(Long idade) {
        return alunoRepository.findAllByIdade(idade);
    }

    @Override
    public List<Aluno> filter(String nome, Long idade, String documento) {
        if (nome != null && idade == null && documento == null) {
            return alunoRepository.findByNome(nome);
        }
        if (nome == null && idade != null && documento == null) {
            return alunoRepository.findAllByIdade(idade);
        }

        Optional<Aluno> aluno = alunoRepository
                .findByNomeAndIdadeAndDocumento(nome, idade, documento);

        if (aluno.isEmpty()) {
            return List.of();
        }

        return List.of(aluno.get());
    }
}

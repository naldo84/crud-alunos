package com.letscode.alunos.service;

import com.letscode.alunos.entity.Aluno;
import com.letscode.alunos.repository.AlunoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
}

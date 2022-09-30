package com.letscode.alunos.repository;

import com.letscode.alunos.entity.Aluno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AlunoRepository extends JpaRepository<Aluno, Long> {

    @Query(value = "select * from alunos as a where a.nome = :nome",
            nativeQuery = true)
    List<Aluno> findByNome(@Param("nome") String nome);


    List<Aluno> findAllByIdade(Long idade);

    /**
     * select * from alunos where documento = "1234"
     * @param documento
     * @return
     */
    Aluno findByDocumento(String documento);

    Aluno findByNomeAndIdade(String nome, Long idade);

    Optional<Aluno> findByNomeAndIdadeAndDocumento(String nome, Long idade, String documento);

    @Query(value = "select * from alunos as a where a.nome = :nome and a.idade = :idade " +
            "and a.documento = :documento",
            nativeQuery = true)
    Aluno filters(@Param("nome") String nome, @Param("idade") Long idade, @Param("documento") String documento);
}

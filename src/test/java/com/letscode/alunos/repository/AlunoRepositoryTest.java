/*
package com.letscode.alunos.repository;

import com.letscode.alunos.entity.Aluno;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AlunoRepositoryTest {

    @Autowired
    AlunoRepository alunoRepository;

    @Test
    @DisplayName("test name")
    void testName() {
        Aluno aluno = new Aluno(1L, "Jonathan2", 22L,
                "4354354333", "Rua teste 2242");

        Aluno save = alunoRepository.save(aluno);

        Assertions.assertNotNull(save);

    }

}*/

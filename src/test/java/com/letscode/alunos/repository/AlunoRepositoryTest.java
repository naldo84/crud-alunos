package com.letscode.alunos.repository;

import com.letscode.alunos.entity.Aluno;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @DataJpaTest anotação para testar os componentes da camada de persistência.
 * que configurarão automaticamente bancos de dados integrados na memória e verificarão classes @Entity
 * e repositórios Spring Data JPA.
 */

/**
 * A anotação @DataJpaTest não carrega outros beans Spring ( @Components , @Controller , @Service e beans anotados) no ApplicationContext
 */
@DataJpaTest

/**
 * A AutoConfigureTestDatabase.Replace.NONE propriedade de anotação diz ao Spring para não substituir o banco de dados por um banco de dados incorporado.
 * Exemplo de uso com testConatiners onde você pode especificar na classe de teste o banco de dados que vc quer usar exemplo MySQL, POSTGRESS
 */
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AlunoRepositoryTest {

    // DataSource, @JdbcTemplate ou @EntityManager

    @Autowired
    AlunoRepository alunoRepository;

    Aluno aluno;

    @BeforeEach
    void setup() {
        aluno = new Aluno(1L, "Jonathan2", 22L,
                "4354354333", "Rua teste 2242");
    }

    @Test
    @DisplayName("Deve salvar um aluno")
    void deveSalvarUmAluno() {
        Aluno aluno1 = alunoRepository.save(aluno);
        assertNotNull(aluno1);
        assertEquals("Jonathan2", aluno1.getNome());
    }

    @Test
    @DisplayName("Deve retornar a lista de alunos")
    void deveRetornarAListaDeAlunos() {
        List<Aluno> alunos = alunoRepository.findAll();

        assertEquals(0, alunos.size());
    }

    @Test
    @DisplayName("Deve retornar uma lista de tamanho 2")
    void deveRetornarUmaListaDeTamanho2() {
        Aluno aluno1 = new Aluno(2L, "Jorge", 24L,
                "435435433867673", "Rua teste2 1");
        Aluno aluno2 = new Aluno(2L, "Augusto", 25L,
                "435435433867679883", "Rua teste3 1");
        //Cria uma lista
        List<Aluno> alunos = new ArrayList<>();
        //Add alunos na lista
        alunos.add(aluno);
        alunos.add(aluno1);
        alunos.add(aluno2);

        //Percorre a lista
        for (Aluno a : alunos) {
            alunoRepository.save(a);
        }

        List<Aluno> alunoList = alunoRepository.findAll();

        assertEquals(2, alunoList.size());
        assertEquals("Jonathan2", alunoList.get(0).getNome());
        assertEquals("Augusto", alunoList.get(1).getNome());

//        Aluno first = alunos.stream().findFirst().get();
//        Assertions.assertEquals("Jonathan2", first.getNome());
    }


    @Test
    @DisplayName("Deve buscar o aluno pelo nome")
    void deveBuscarOAlunoPeloNome() {
        aluno.setNome("teste");
        alunoRepository.save(aluno);
        List<Aluno> byNome = alunoRepository.findByNome("teste");
        assertEquals(1, byNome.size());
    }

    @Test
    @DisplayName("Deve buscar pela idade")
    void deveBuscarPelaIdade() {
        aluno.setIdade(23L);
        Aluno aluno1 = new Aluno(2L, "Jorge", 23L,
                "435435433867673", "Rua teste2 1");
        Aluno aluno2 = new Aluno(3L, "Augusto", 23L,
                "435435433867679883", "Rua teste3 1");
        //Cria uma lista
        List<Aluno> alunos = new ArrayList<>();
        //Add alunos na lista
        alunos.add(aluno);
        alunos.add(aluno1);
        alunos.add(aluno2);

        for (Aluno a : alunos) {
            alunoRepository.save(a);
        }

        List<Aluno> byIdade = alunoRepository.findAllByIdade(23L);
        assertEquals(3, byIdade.size());
        assertEquals("Jonathan2", byIdade.get(0).getNome());
        Aluno aluno4 = byIdade.stream().filter(aluno3 ->
                        Objects.equals(aluno3.getNome(), "Augusto"))
                .findAny().get();

        assertEquals(23L, aluno4.getIdade());
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia quando nao tiver alunos com a idade especificada cadastros")
    void deveRetornarUmaListaVaziaQuandoNaoTiverAlunosComAIdadeEspecificadaCadastros() {
        aluno.setIdade(30L);
        alunoRepository.save(aluno);

        assertEquals(0, alunoRepository.findAllByIdade(20L).size());
    }

    @Test
    @DisplayName("Deve testar os parametros")
    void deveTestarOsParametros() {
        Aluno aluno2 = alunoRepository.save(aluno);
        Optional<Aluno> aluno1 = alunoRepository.findByNomeAndIdadeAndDocumento("Jonathan2", 22L,
                "4354354333");

        Assumptions.assumeTrue(aluno1.isPresent());

        assertAll(
                () -> assertEquals(aluno2.getId(), aluno1.get().getId()),
                () -> assertEquals("Jonathan2", aluno1.get().getNome()),
                () -> assertEquals(22L, aluno1.get().getIdade()),
                () -> assertEquals("4354354333", aluno1.get().getDocumento()),
                () -> assertEquals("Rua teste 2242", aluno1.get().getEndereco())
        );

    }

    @ParameterizedTest
    @ValueSource(longs = {23, 45, 67, 60, 33})
    @DisplayName("Deve buscar um aluno com idades passadas por parametro")
    void deveBuscarUmAlunoComIdadesPassadasPorParametro(Long idade) {
        aluno.setIdade(idade);
        alunoRepository.save(aluno);
        List<Aluno> alunoList = alunoRepository.findAllByIdade(idade);
        assertEquals(idade, alunoList.get(0).getIdade());
    }

}

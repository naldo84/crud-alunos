package com.letscode.alunos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.letscode.alunos.entity.Aluno;
import com.letscode.alunos.service.AlunoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import java.util.Collections;
import java.util.List;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)

/**
 * WebMvcTest -> é a anotação usada para iniciar o contexto de aplicação
 * que contém apenas os beans necessários para testar um controller
 */

/**
 * Estamos passando o AlunoController na anotação para restringir o contexto da aplicação
 * se não especificarmos o Spring Boot irá incluir todos o controllers no contexto da aplicação.
 * Assim, precisamos incluir ou simular  todos os beans dos quais qualquer controller depende
 */
//@WebMvcTest(controllers = {AlunoController.class, outros cotrollers})
//@WebMvcTest
@WebMvcTest(AlunoController.class)
class AlunoControllerTest {

    /**
     * Agora podemos @Autowired todos os beans que precisamos do contexto do aplicativo.
     * O Spring fornece automaticamente beans como MockMvc, ObjectMapper
     */

    /**
     * Usamos a anotação @MockBean para simular a logica de negocios (service), pois não queremos
     * testar a integração do controller com o serviço, mas sim entre o controller e o HTTP.
     *  @MockBean substitui automaticamente o bean do mesmo tipo no contexto do aplicativo por um mock do Mockito.
     */

    /**
     * Diferença
     * Os campos anotados com @Mock serão inicializados automaticamente com uma instância simulada de seu tipo,
     * assim como chamaríamos Mockito.mock() manualmente.
     * A anotação @MockBean fará com que o Spring procure um bean do tipo existente AlunoService no contexto do aplicação
     * se existir ele substituirá esse bean por um mock do Mockito.
     */
    @MockBean
    AlunoService alunoService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    Aluno aluno;

    @BeforeEach
    void setup() {
        //Nova instancia do aluno
        aluno = new Aluno(1L, "Jonathan2", 22L,
                "4354354333", "Rua teste 2242");
    }

    @Test
    @DisplayName("Deve cadastra um novo aluno")
    void deveCadastraUmNovoAluno() throws Exception {

        // Simula o metodo salvar
        when(alunoService.salvar(any())).thenReturn(aluno);

        // chamar nosso endpoint
        MvcResult result = mockMvc.perform(post("/alunos") // especifica url
                        .contentType(MediaType.APPLICATION_JSON) //content_type
                        .content(objectMapper.writeValueAsString(aluno))) // body
                .andExpect(status().isCreated()) // 201
                .andReturn(); // retorno

        //Resposta -> String
        String body = result.getResponse().getContentAsString();

        // String -> Aluno.class
        Aluno alunoResultado = objectMapper.readValue(body, Aluno.class);

        //Validacoes
        Assertions.assertAll(
                () -> Assertions.assertEquals(aluno.getNome(), alunoResultado.getNome()),
                () -> Assertions.assertEquals(aluno.getId(), alunoResultado.getId()),
                () -> Assertions.assertEquals(aluno.getDocumento(), alunoResultado.getDocumento())
        );
    }

    @Test
    @DisplayName("Deve verificar o valor do nome do aluno que foi enviado")
    void deveVerificarOValorDoNomeDoAlunoQueFoiEnviado() throws Exception {

        // Simula o metodo salvar
        when(alunoService.salvar(any())).thenReturn(aluno);

        // chamar nosso endpoint
        MvcResult result = mockMvc.perform(post("/alunos") // especifica url
                        .contentType(MediaType.APPLICATION_JSON) //content_type
                        .content(objectMapper.writeValueAsString(aluno))) // body
                .andExpect(status().isCreated()) // 201
                .andReturn(); // retorno

        //ArgumentCaptor para capturar o Aluno objeto que foi enviado para o metodo salvar e afirmar que ele contém os valores esperados.
        ArgumentCaptor<Aluno> alunoArgumentCaptor = ArgumentCaptor.forClass(Aluno.class);
        verify(alunoService, times(1)).salvar(alunoArgumentCaptor.capture());
        Assertions.assertEquals(aluno.getNome(), alunoArgumentCaptor.getValue().getNome());
    }

    @Test
    @DisplayName("Testa fluxo com nome null")
    void testaFluxoComNomeNull() throws Exception {
        aluno.setNome(null);
        MvcResult result = mockMvc.perform(post("/alunos") //url
                .contentType(MediaType.APPLICATION_JSON) // content type
                .content(objectMapper.writeValueAsString(aluno)) //body request
        ).andExpect(status().is4xxClientError()).andReturn();//erro
    }


    @Test
    @DisplayName("Deve buscar todos os alunos")
    void deveBuscarTodosOsAlunos() throws Exception {
        // Transformo o aluno numa lista
        List<Aluno> alunos = Collections.singletonList(aluno);

        // Simulo o resultado da camada de negocios
        when(alunoService.buscaTodos()).thenReturn(alunos);

        MvcResult result = mockMvc.perform(get("/alunos") // especifica o metodo http que será chamado passando a url
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsBytes(aluno))) // body json
                .andExpect(status().isOk()) // 200 OK
                .andReturn();

        String response = result.getResponse().getContentAsString();

        // Objeto > Json
        String json = objectMapper.writeValueAsString(alunos);
        Assertions.assertEquals(json, response);
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia")
    void deveRetornarUmaListaVazia() throws Exception {
        when(alunoService.buscaTodos()).thenReturn(List.of());

        //Retorna uma lista vazia
        MvcResult result = mockMvc.perform(get("/alunos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        //response -> lista
        var list = objectMapper.readValue(result.getResponse().getContentAsString(), List.class);

        // Verifica se a lista esta vazia
        Assertions.assertEquals(0, list.size());
    }

    @Test
    @DisplayName("Deve retorna o aluno quando buscar por id")
    void deveRetornaOAlunoQuandoBuscarPorId() throws Exception {
//        Optional<Aluno> aluno1 = Optional.of(aluno);
        when(alunoService.buscaPorId(anyLong())).thenReturn(aluno);

        MvcResult result = mockMvc.perform(get("/alunos/{id}", aluno.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Aluno aluno1 = objectMapper.readValue(result.getResponse().getContentAsString(), Aluno.class);

        Assertions.assertEquals("Jonathan2", aluno1.getNome());
    }

    @Test
    @DisplayName("Deve alterar o nome ao buscar pelo ID")
    void deveAlterarONomeAoBuscarPeloID() throws Exception {
        when(alunoService.alterarAluno(anyLong(), anyString())).thenReturn(aluno);

        MvcResult result = mockMvc.perform(patch("/alunos/{id}/{nome}",aluno.getId(),aluno.getNome())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        Aluno aluno1 = objectMapper.readValue(result.getResponse().getContentAsString(), aluno.getClass());
        Assertions.assertEquals("Jonathan2", aluno1.getNome());
    }

    @Test
    @DisplayName("Deve retornar mensagem aluno deletado ao buscar por id")
    public void deveRetornarMensagemAlunoDeletadoAoBuscarPorId() throws Exception {
        when(alunoService.delete(anyLong())).thenReturn("Aluno deletado");

        MvcResult result = mockMvc.perform(delete("/alunos/{id}", aluno.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        String resposta = result.getResponse().getContentAsString();
        Assertions.assertEquals("Aluno deletado",resposta);
    }

    @Test
    @DisplayName("Deve apresentar lista de aluno ao buscar por nome")
    public void deveApresentarListaDeAlunoAoBuscarPorNome() throws Exception {
        when(alunoService.buscaPorNome(anyString())).thenReturn(List.of(aluno));

        MvcResult result = mockMvc.perform(get("/alunos/nomes")
                        .param("nome","NaldoN")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        var lista = objectMapper.readValue(result.getResponse().getContentAsString(),List.class);

        Assertions.assertEquals(1,lista.size());

    }

    @Test
    @DisplayName("Deve apresentar uma lista de aluno ao buscar por idade")
    public void deveApresentarUmaListaDeAlunoQuandoBuscarPorIdade() throws Exception {
        when(alunoService.buscaPorIdade(anyLong())).thenReturn(List.of(aluno));

        MvcResult result = mockMvc.perform(get("/alunos/idades")
                        .param("idade","18")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        var lista = objectMapper.readValue(result.getResponse().getContentAsString(),List.class);

        Assertions.assertEquals(1,lista.size());

    }

    @Test
    @DisplayName("Deve apresentar lista de aluno ao buscar todos os parametros")
    public void deveApresenarListaDeAlunoAoBuscarTodosOsParametros() throws Exception {
        when(alunoService.filter(anyString(),anyLong(),anyString())).thenReturn(List.of(aluno));

        MvcResult result = mockMvc.perform(get("/alunos/fiters")
                        .param("nome","Naldo")
                        .param("idade", "18")
                        .param("documento","123456789")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
        var list = objectMapper.readValue(result.getResponse().getContentAsString(),List.class);

        Assertions.assertEquals(1 , list.size());
    }
}
package com.letscode.alunos.controller;

import com.letscode.alunos.entity.Aluno;
import com.letscode.alunos.service.AlunoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AlunoController.class)
class AlunoControllerTest {

    @MockBean
    AlunoService alunoService;

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("test name")
    void testName() throws Exception {

        Aluno aluno = new Aluno(1L, "Jonathan2", 22L, "4354354333", "Rua teste 2242");

        List<Aluno> alunos = Arrays.asList(aluno);

        Mockito.when(alunoService.buscaTodos()).thenReturn(alunos);

        mockMvc.perform(MockMvcRequestBuilders.get("/alunos"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
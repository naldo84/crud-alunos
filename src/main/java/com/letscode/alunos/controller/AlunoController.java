package com.letscode.alunos.controller;

import com.letscode.alunos.entity.Aluno;
import com.letscode.alunos.service.AlunoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Aluno salvar(@RequestBody Aluno aluno) {
        return alunoService.salvar(aluno);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Aluno> consultaTodos() {
        return alunoService.buscaTodos();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Aluno buscaPorId(@PathVariable(value = "id") Long id) throws Exception {
        return alunoService.buscaPorId(id);
    }

       	/* Até as 20:30
	       Criar um endpoint(url) pra alterar um dado @Path
	       Criar um endpoint(url) pra deletar um aluno @Delete
	    */

}

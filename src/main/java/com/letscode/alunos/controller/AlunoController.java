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

    @PatchMapping("/{id}/{nome}")
    @ResponseStatus(HttpStatus.PARTIAL_CONTENT)
    public Aluno altera(@PathVariable(value = "id") Long id,
                        @PathVariable(value = "nome") String nome) throws Exception {
        return alunoService.alterarAluno(id, nome);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String deleta(@PathVariable(value = "id") Long id) throws Exception {
        return alunoService.delete(id);
    }
}

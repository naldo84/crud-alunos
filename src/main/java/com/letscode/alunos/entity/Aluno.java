package com.letscode.alunos.entity;

import javax.persistence.*;

@Entity // Indentifico que sera minha entidade
@Table(name = "alunos") // nome da minha tabela
public class Aluno {
    @Id //chave da tabela
    @GeneratedValue(strategy = GenerationType.AUTO) // gera randomicamente
    private Long id;
    private String nome;
    private Long idade;
    private String documento;
    private String endereco;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getIdade() {
        return idade;
    }

    public void setIdade(Long idade) {
        this.idade = idade;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}

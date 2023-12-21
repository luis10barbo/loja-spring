package com.luisbb.loja.springboot.jpa.entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String nome;
    private String descricao;
    private double preco;
    private String imagem;

    private double avaliacao = 0.0;
    private int numAvaliacoes = 0;
}

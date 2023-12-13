package com.luisbb.loja.springboot.jpa.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ProdutoOrdem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private double preco;
    private int quantidade;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idProduto")
    private Produto produto;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "idCarrinho")
    private Ordem ordem;

    public void setProduto(ProdutoCarrinho produtoCarrinho) {
        this.produto = produtoCarrinho.getProduto();
        this.preco = produto.getPreco();
        this.quantidade = produtoCarrinho.getQuantidade();
    }
}

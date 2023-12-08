package com.luisbb.loja.springboot.jpa.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class ProdutoCarrinho {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idProduto")
    Produto produto;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "idCarrinho")
    @JsonIgnore
    Carrinho carrinho;
    int quantidade;
}

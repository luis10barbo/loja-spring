package com.luisbb.loja.springboot.jpa.entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AvaliacaoProduto {
    @Id
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idProduto")
    private Produto produto;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private Usuario usuario;

    private float avaliacao;
}

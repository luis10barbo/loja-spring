package com.luisbb.loja.springboot.jpa.entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter @NoArgsConstructor
public class Carrinho {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "CarrinhoProduto",
            joinColumns = @JoinColumn(
                    name = "idCarrinho"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "idProduto"
            )
    )
    private Set<Produto> produtos = new HashSet<>();
}

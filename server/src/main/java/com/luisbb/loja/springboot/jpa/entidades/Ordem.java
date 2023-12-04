package com.luisbb.loja.springboot.jpa.entidades;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Ordem {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private boolean entregaRealizada;

    @Temporal(TemporalType.TIMESTAMP)
    private Date momentoCriacao;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            joinColumns = @JoinColumn(
                    name = "idOrdem"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "idProduto"
            )
    )
    private Set<Produto> produtos = new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idTransportadora")
    private Transportadora transportadora;
}

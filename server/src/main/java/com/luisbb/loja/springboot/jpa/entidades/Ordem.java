package com.luisbb.loja.springboot.jpa.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Temporal(TemporalType.TIMESTAMP)
    private Date momentoFinalizada = null;

    @Temporal(TemporalType.TIMESTAMP)
    private Date momentoCriacao = new Date();

    @Temporal(TemporalType.TIMESTAMP)
    private Date momentoEsperadoFinalizada = new Date();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idTransportadora")
    private Transportadora transportadora;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH, mappedBy = "ordem")
    private Set<ProdutoOrdem> produtosOrdem = new HashSet<>();

    private boolean cancelada = false;
    private double frete;
}

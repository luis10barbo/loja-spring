package com.luisbb.loja.springboot.jpa.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter @NoArgsConstructor
public class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idUsuario;
    @Column(unique = true)
    private String apelido;

    @JsonIgnore
    private String hashSenha;
    @JsonIgnore
    private String saltSenha;
    @JsonIgnore
    private String email;

    private Double saldo;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "usuario", fetch = FetchType.EAGER)
    private Carrinho carrinho = new Carrinho(this);

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.DETACH, mappedBy = "usuario")
    private Set<Ordem> ordens = new HashSet<>();

    private boolean admin = false;
}

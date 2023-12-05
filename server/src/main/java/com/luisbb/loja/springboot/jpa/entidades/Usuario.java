package com.luisbb.loja.springboot.jpa.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class Usuario {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idUsuario;
    private String apelido;

    @JsonIgnore
    private String hashSenha;
    @JsonIgnore
    private String saltSenha;
    @JsonIgnore
    private String email;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Carrinho carrinho;
}

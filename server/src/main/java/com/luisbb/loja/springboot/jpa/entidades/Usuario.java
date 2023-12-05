package com.luisbb.loja.springboot.jpa.entidades;

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
    private String hashSenha;
    private String saltSenha;
    private String email;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "usuario")
    private Carrinho carrinho;
}

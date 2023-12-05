package com.luisbb.loja.springboot.jpa.repositorios;

import com.luisbb.loja.springboot.jpa.entidades.Usuario;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RepositorioUsuario extends CrudRepository<Usuario, Long> {

    Optional<Usuario> findByApelido(String apelido);
}

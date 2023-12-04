package com.luisbb.loja.springboot.jpa.repositorios;

import com.luisbb.loja.springboot.jpa.entidades.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface RepositorioUsuario extends CrudRepository<Usuario, Long> {
    Usuario findById(long id);
}

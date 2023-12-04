package com.luisbb.loja.springboot.jpa.repositorios;

import com.luisbb.loja.springboot.jpa.entidades.Produto;
import org.springframework.data.repository.CrudRepository;

public interface RepositorioProduto extends CrudRepository<Produto, Long> {
}

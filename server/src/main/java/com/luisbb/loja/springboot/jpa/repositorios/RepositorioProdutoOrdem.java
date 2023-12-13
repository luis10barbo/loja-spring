package com.luisbb.loja.springboot.jpa.repositorios;

import com.luisbb.loja.springboot.jpa.entidades.ProdutoOrdem;
import org.springframework.data.repository.CrudRepository;

public interface RepositorioProdutoOrdem extends CrudRepository<ProdutoOrdem, Long> {

}

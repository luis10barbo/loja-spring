package com.luisbb.loja.springboot.jpa.repositorios;

import com.luisbb.loja.springboot.jpa.entidades.Carrinho;
import org.springframework.data.repository.CrudRepository;

public interface RepositorioCarrinho extends CrudRepository<Carrinho, Long> {
}

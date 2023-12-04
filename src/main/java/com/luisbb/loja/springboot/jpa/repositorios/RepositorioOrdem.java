package com.luisbb.loja.springboot.jpa.repositorios;

import com.luisbb.loja.springboot.jpa.entidades.Ordem;
import org.springframework.data.repository.CrudRepository;

public interface RepositorioOrdem extends CrudRepository<Ordem, Long> {
}

package com.luisbb.loja.springboot.jpa.repositorios;

import com.luisbb.loja.springboot.jpa.entidades.Transportadora;
import org.springframework.data.repository.CrudRepository;

public interface RepositorioTransportadora extends CrudRepository<Transportadora, Long> {
}

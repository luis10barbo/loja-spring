package com.luisbb.loja.springboot.jpa.repositorios;

import com.luisbb.loja.springboot.jpa.entidades.Sessao;
import org.springframework.data.repository.CrudRepository;

public interface RepositorioSessao extends CrudRepository<Sessao, String> {
}

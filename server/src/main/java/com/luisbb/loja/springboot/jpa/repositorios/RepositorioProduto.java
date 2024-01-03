package com.luisbb.loja.springboot.jpa.repositorios;

import com.luisbb.loja.springboot.jpa.entidades.Ordem;
import com.luisbb.loja.springboot.jpa.entidades.Produto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface RepositorioProduto extends CrudRepository<Produto, Long> {

    @Transactional
    @Query(value = "SELECT * FROM produto WHERE lower(nome) LIKE lower(:pesquisa) OR lower(descricao) LIKE lower(:pesquisa)", nativeQuery = true)
    Set<Produto> find(@Param("pesquisa") String pesquisa);
}

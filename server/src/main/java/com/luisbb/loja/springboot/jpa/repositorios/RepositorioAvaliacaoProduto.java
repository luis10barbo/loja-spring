package com.luisbb.loja.springboot.jpa.repositorios;

import com.luisbb.loja.springboot.jpa.entidades.AvaliacaoProduto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface RepositorioAvaliacaoProduto extends CrudRepository<AvaliacaoProduto, Long> {
    @Transactional
    @Query(value = "SELECT * FROM avaliacao_produto ap WHERE ap.id_usuario = :id_usuario AND ap.id_produto = :id_produto LIMIT 1", nativeQuery = true)
    Optional<AvaliacaoProduto> findByUsuarioEProduto(@Param("id_usuario") long idUsuario, @Param("id_produto") long idProduto);

    @Transactional
    @Query(value = "SELECT * FROM avaliacao_produto ap WHERE ap.id_produto = :id_produto", nativeQuery = true)
    Set<AvaliacaoProduto> findByProduto(@Param("id_produto") long idProduto);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM avaliacao_produto ap WHERE ap.id = :id", nativeQuery = true)
    void deleteById(@Param("id") long id);
}

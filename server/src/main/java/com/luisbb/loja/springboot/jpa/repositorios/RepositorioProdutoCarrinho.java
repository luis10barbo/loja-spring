package com.luisbb.loja.springboot.jpa.repositorios;

import com.luisbb.loja.springboot.jpa.entidades.ProdutoCarrinho;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RepositorioProdutoCarrinho extends CrudRepository<ProdutoCarrinho, String> {
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM produto_carrinho pc WHERE pc.id_produto = :id_produto AND pc.id_carrinho = :id_carrinho", nativeQuery = true)
    void deleteByIdProdutoAndCarrinho(@Param("id_produto") long idProduto, @Param("id_carrinho") long idCarrinho);
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM produto_carrinho pc WHERE pc.id_carrinho = :id_carrinho", nativeQuery = true)
    void deleteByIdCarrinho(@Param("id_carrinho") long idCarrinho);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO produto_carrinho (id, id_produto, id_carrinho, quantidade) VALUES (:id, :id_produto, :id_carrinho, :quantidade)", nativeQuery = true)
    void addProdutoInCarrinho(@Param("id") String id, @Param("id_produto") long idProduto, @Param("id_carrinho") long idCarrinho, @Param("quantidade") int quantidade);

    @Transactional
    @Modifying
    @Query(value = "update produto_carrinho set quantidade = :quantidade where id_produto = :id_produto", nativeQuery = true)
    void setQuantidade(@Param("id_produto") long idProduto, @Param("quantidade") long quantidade);
}

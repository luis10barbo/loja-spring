package com.luisbb.loja.springboot.jpa.repositorios;

import com.luisbb.loja.springboot.jpa.entidades.Ordem;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface RepositorioOrdem extends CrudRepository<Ordem, Long> {
    @Transactional
    @Query(value = "SELECT * FROM ordem WHERE id_usuario = :id_usuario", nativeQuery = true)
    Optional<Set<Ordem>> findByUserId(@Param("id_usuario") long idUsuario);
    @Transactional
    @Query(value = "SELECT * FROM ordem WHERE id_usuario = :id_usuario AND id = :id", nativeQuery = true)
    Optional<Ordem> findByIdAndUserId(@Param("id") long id, @Param("id_usuario") long idUsuario);
}

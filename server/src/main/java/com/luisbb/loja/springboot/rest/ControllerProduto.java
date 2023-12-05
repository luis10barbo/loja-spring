package com.luisbb.loja.springboot.rest;

import com.luisbb.loja.springboot.jpa.entidades.Produto;
import com.luisbb.loja.springboot.jpa.repositorios.RepositorioProduto;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/produtos")
public class ControllerProduto {
    RepositorioProduto repositorioProduto;
    public ControllerProduto(RepositorioProduto repositorioProduto) {
        this.repositorioProduto = repositorioProduto;
    }
    @GetMapping("/all")
    public Iterable<Produto> adquirirTodos() {
        return repositorioProduto.findAll();
    }

    @GetMapping
    public Optional<Produto> adquirir(@RequestParam long id) {
        return repositorioProduto.findById(id);
    }

    @PostMapping
    public Produto criar(@RequestBody Produto produto) {
        return repositorioProduto.save(produto);
    }
}

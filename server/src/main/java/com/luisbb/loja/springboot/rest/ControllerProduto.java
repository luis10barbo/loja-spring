package com.luisbb.loja.springboot.rest;

import com.luisbb.loja.springboot.jpa.entidades.Produto;
import com.luisbb.loja.springboot.jpa.repositorios.RepositorioProduto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

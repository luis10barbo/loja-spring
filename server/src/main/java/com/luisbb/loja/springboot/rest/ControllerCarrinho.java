package com.luisbb.loja.springboot.rest;

import com.luisbb.loja.springboot.jpa.entidades.Carrinho;
import com.luisbb.loja.springboot.jpa.entidades.Produto;
import com.luisbb.loja.springboot.jpa.entidades.Usuario;
import com.luisbb.loja.springboot.jpa.repositorios.RepositorioCarrinho;
import com.luisbb.loja.springboot.jpa.repositorios.RepositorioUsuario;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/carrinhos")
public class ControllerCarrinho {
    private final RepositorioUsuario repositorioUsuario;
    private final RepositorioCarrinho repositorioCarrinho;

    public ControllerCarrinho(RepositorioUsuario repositorioUsuario, RepositorioCarrinho repositorioCarrinho) {
        this.repositorioUsuario = repositorioUsuario;
        this.repositorioCarrinho = repositorioCarrinho;
    }
    @PostMapping("/add")
    public boolean adicionarProduto(HttpServletRequest request, @RequestBody Produto produto) {
        Optional<Usuario> optUsuario = ControllerUsuario.adquirirUsuario(repositorioUsuario, request.getSession());
        if (optUsuario.isEmpty()) {
            return false;
        }

        Usuario usuario = optUsuario.get();
        Carrinho carrinho = usuario.getCarrinho();
        if (carrinho.getProdutos().stream().noneMatch(p -> p.getId() == produto.getId())) {
            carrinho.getProdutos().add(produto);
        }
        repositorioCarrinho.save(carrinho);
        return true;
    }

    @PostMapping("/remover")
    public boolean removerProduto(HttpServletRequest request, @RequestBody Produto produto) {
        Optional<Usuario> optUsuario = ControllerUsuario.adquirirUsuario(repositorioUsuario, request.getSession());
        if (optUsuario.isEmpty()) {
            return false;
        }

        Usuario usuario = optUsuario.get();
        Carrinho carrinho = usuario.getCarrinho();

        carrinho.getProdutos().removeIf(p -> p.getId() == produto.getId());

        repositorioCarrinho.save(carrinho);
        return true;
    }

    @PostMapping("/removerTodos")
    public boolean removerTodosProdutos(HttpServletRequest request) {
        Optional<Usuario> optUsuario = ControllerUsuario.adquirirUsuario(repositorioUsuario, request.getSession());
        if (optUsuario.isEmpty()) {
            return false;
        }
        Usuario usuario = optUsuario.get();
        Carrinho carrinho = usuario.getCarrinho();
        carrinho.getProdutos().clear();

        repositorioCarrinho.save(carrinho);
        return true;
    }
}

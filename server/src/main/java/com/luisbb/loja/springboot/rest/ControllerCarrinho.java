package com.luisbb.loja.springboot.rest;

import com.luisbb.loja.springboot.jpa.entidades.Carrinho;
import com.luisbb.loja.springboot.jpa.entidades.Produto;
import com.luisbb.loja.springboot.jpa.entidades.ProdutoCarrinho;
import com.luisbb.loja.springboot.jpa.entidades.Usuario;
import com.luisbb.loja.springboot.jpa.repositorios.RepositorioCarrinho;
import com.luisbb.loja.springboot.jpa.repositorios.RepositorioProdutoCarrinho;
import com.luisbb.loja.springboot.jpa.repositorios.RepositorioUsuario;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/carrinhos")
public class ControllerCarrinho {
    private final RepositorioUsuario repositorioUsuario;
    private final RepositorioCarrinho repositorioCarrinho;
    private final RepositorioProdutoCarrinho repositorioProdutoCarrinho;

    public ControllerCarrinho(RepositorioUsuario repositorioUsuario, RepositorioCarrinho repositorioCarrinho, RepositorioProdutoCarrinho repositorioProdutoCarrinho) {
        this.repositorioUsuario = repositorioUsuario;
        this.repositorioCarrinho = repositorioCarrinho;
        this.repositorioProdutoCarrinho = repositorioProdutoCarrinho;
    }
    @PostMapping("/add")
    public boolean adicionarProduto(HttpServletRequest request, @RequestBody Produto produto) {
        Optional<Usuario> optUsuario = ControllerUsuario.adquirirUsuario(repositorioUsuario, request.getSession());
        if (optUsuario.isEmpty()) {
            return false;
        }

        Usuario usuario = optUsuario.get();
        Carrinho carrinho = usuario.getCarrinho();
        if (carrinho.getProdutos().stream().noneMatch(p -> p.getProduto().getId() == produto.getId())) {
//            ProdutoCarrinho produtoCarrinho = new ProdutoCarrinho();
//            produtoCarrinho.setCarrinho(carrinho);
//            produtoCarrinho.setProduto(produto);
//            produtoCarrinho.setQuantidade(1);
//            carrinho.getProdutos().add(produtoCarrinho);
//            repositorioCarrinho.save(carrinho);
            repositorioProdutoCarrinho.addProdutoInCarrinho(UUID.randomUUID().toString(), produto.getId(), carrinho.getId(), 1);
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
//        repositorioProdutoCarrinho.save(produto);
//        Optional<ProdutoCarrinho> teste = repositorioProdutoCarrinho.findById(produto.getId());
        // TODO: arrumar delete so estar dando update
        repositorioProdutoCarrinho.deleteByIdProdutoAndCarrinho(produto.getId(), usuario.getCarrinho().getId());
//        carrinho.getProdutos().forEach(produtoCarrinho -> {
//
////                repositorioProdutoCarrinho.deleteById(produtoCarrinho.getUuid());
//
//        });

//        Carrinho teste = repositorioCarrinho.save(carrinho);
//        Optional<Carrinho> fetch = repositorioCarrinho.findById(carrinho.getId());
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
//        for(ProdutoCarrinho produtoCarrinho: carrinho.getProdutos()) {
//            this.repositorioProdutoCarrinho.deleteById(produtoCarrinho.getId());
//        }
        this.repositorioProdutoCarrinho.deleteByIdCarrinho(carrinho.getId());
        return true;
    }
    @Getter @Setter @NoArgsConstructor
    static class AlterarProduto {
        private int quantidade;
    }
    @PostMapping("/alterarProduto")
    public boolean alterarProduto(HttpServletRequest request, @RequestBody AlterarProduto alterarProduto) {
        Optional<Usuario> optUsuario = ControllerUsuario.adquirirUsuario(repositorioUsuario, request.getSession());
        if (optUsuario.isEmpty()) {
            return false;
        }

        Usuario usuario = optUsuario.get();
        Carrinho carrinho = usuario.getCarrinho();
        this.repositorioProdutoCarrinho.setQuantidade(carrinho.getId(), alterarProduto.quantidade);
        return true;
    }
}

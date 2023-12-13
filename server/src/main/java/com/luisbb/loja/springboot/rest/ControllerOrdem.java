package com.luisbb.loja.springboot.rest;

import com.luisbb.loja.springboot.jpa.entidades.*;
import com.luisbb.loja.springboot.jpa.repositorios.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/ordens")
public class ControllerOrdem {

    private final RepositorioSessao repositorioSessao;
    private final RepositorioOrdem repositorioOrdem;
    private final RepositorioTransportadora repositorioTransportadora;
    private final RepositorioProdutoCarrinho repositorioProdutoCarrinho;
    private final RepositorioProdutoOrdem repositorioProdutoOrdem;

    public ControllerOrdem(RepositorioProdutoCarrinho repositorioProdutoCarrinho, RepositorioProdutoOrdem repositorioProdutoOrdem,  RepositorioSessao repositorioSessao, RepositorioOrdem repositorioOrdem, RepositorioTransportadora repositorioTransportadora) {
        this.repositorioSessao = repositorioSessao;
        this.repositorioOrdem = repositorioOrdem;
        this.repositorioTransportadora = repositorioTransportadora;
        this.repositorioProdutoCarrinho = repositorioProdutoCarrinho;
        this.repositorioProdutoOrdem = repositorioProdutoOrdem;
    }

    @PostMapping("/finalizar")
    public Ordem finalizar(HttpServletRequest request, @RequestBody long idTransportadora) {
        Optional<Usuario> optUsuario = ControllerUsuario.adquirirUsuario(repositorioSessao, request.getSession());
        if (optUsuario.isEmpty()) {
            return null;
        }
        Usuario usuario = optUsuario.get();

        Set<ProdutoCarrinho> produtosCarrinho = usuario.getCarrinho().getProdutos();

        Optional<Transportadora> optTransportadora = repositorioTransportadora.findById(idTransportadora);
        if (optTransportadora.isEmpty()) {
            return null;
        }
        Transportadora transportadora = optTransportadora.get();

        Ordem ordem = new Ordem();
        Set<ProdutoOrdem> produtosOrdem = ordem.getProdutosOrdem();
        for (ProdutoCarrinho produtoCarrinho: produtosCarrinho) {
            ProdutoOrdem produtoOrdem = new ProdutoOrdem();
            produtoOrdem.setOrdem(ordem);
            produtoOrdem.setProduto(produtoCarrinho);

            repositorioProdutoOrdem.save(produtoOrdem);
            produtosOrdem.add(produtoOrdem);
        }

        ordem.setUsuario(usuario);
        ordem.setTransportadora(transportadora);
        ordem.setFrete(transportadora.getValorFrete());
        Ordem res = repositorioOrdem.save(ordem);

        if (res.getId() != 0) {
            repositorioProdutoCarrinho.deleteByIdCarrinho(usuario.getCarrinho().getId());
        }
        return res;
    }

    @PostMapping("/cancelar")
    public boolean cancelar(HttpServletRequest request, @RequestBody long idOrdem) {
        Optional<Usuario> optUsuario = ControllerUsuario.adquirirUsuario(repositorioSessao, request.getSession());
        if (optUsuario.isEmpty()) {
            return false;
        }
        Usuario usuario = optUsuario.get();

        Optional<Ordem> optOrdem = repositorioOrdem.findById(idOrdem);
        if (optOrdem.isEmpty()) {
            return false;
        }

        Ordem ordem = optOrdem.get();
        Usuario usuarioOrdem = ordem.getUsuario();

        if (!Objects.equals(usuarioOrdem.getIdUsuario(), usuario.getIdUsuario())) {
            return false;
        }
        ordem.setCancelada(true);
        Ordem res = repositorioOrdem.save(ordem);

        return res.isCancelada();
    }
}

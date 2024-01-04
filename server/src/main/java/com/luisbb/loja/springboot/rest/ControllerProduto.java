package com.luisbb.loja.springboot.rest;

import com.luisbb.loja.springboot.jpa.entidades.AvaliacaoProduto;
import com.luisbb.loja.springboot.jpa.entidades.Produto;
import com.luisbb.loja.springboot.jpa.entidades.Usuario;
import com.luisbb.loja.springboot.jpa.repositorios.RepositorioAvaliacaoProduto;
import com.luisbb.loja.springboot.jpa.repositorios.RepositorioProduto;
import com.luisbb.loja.springboot.jpa.repositorios.RepositorioSessao;
import com.luisbb.loja.model.retorno.Retorno;
import com.luisbb.loja.model.retorno.RetornoBadRequest;
import com.luisbb.loja.model.retorno.RetornoSucesso;
import com.luisbb.loja.model.retorno.RetornoUnauthorized;
import com.luisbb.loja.springboot.jpa.repositorios.RepositorioUsuario;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/produtos")
public class ControllerProduto {
    RepositorioProduto repositorioProduto;
    RepositorioAvaliacaoProduto repositorioAvaliacaoProduto;
    RepositorioSessao repositorioSessao;
    public ControllerProduto(RepositorioProduto repositorioProduto, RepositorioAvaliacaoProduto repositorioAvaliacaoProduto, RepositorioSessao repositorioSessao) {
        this.repositorioProduto = repositorioProduto;
        this.repositorioAvaliacaoProduto = repositorioAvaliacaoProduto;
        this.repositorioSessao = repositorioSessao;
    }
    @GetMapping("/all")
    public Iterable<Produto> adquirirTodos(@RequestParam String s) {
        return repositorioProduto.find("%"+s+"%");
    }

    @GetMapping
    public Optional<Produto> adquirir(@RequestParam long id) {
        return repositorioProduto.findById(id);
    }

    @PostMapping
    public Retorno criar(HttpServletRequest request, HttpServletResponse response, @RequestBody Produto produto) {
        Optional<Usuario> optUsuario = ControllerUsuario.adquirirUsuario(repositorioSessao, request.getSession());
        if (optUsuario.isEmpty()) {
            return new RetornoUnauthorized(response);
        }

        Usuario usuario = optUsuario.get();
        if (!usuario.isAdmin()) {
            return new RetornoUnauthorized(response, "Voce precisa ser admin para criar produtos");
        }

        return new RetornoSucesso(response, repositorioProduto.save(produto));
    }

    public static class BodyRemoverAvaliar {
        public long idProduto;
    }


    @PostMapping("/removerAvaliacao")
    public Retorno removerAvaliacao(@RequestBody BodyAvaliar bodyAvaliar, HttpServletRequest request, HttpServletResponse response) {
        Optional<Usuario> optUsuario = ControllerUsuario.adquirirUsuario(repositorioSessao, request.getSession());
        if (optUsuario.isEmpty()) {
            return new RetornoUnauthorized(response);
        }
        Usuario usuario = optUsuario.get();

        Optional<Produto> optProduto = repositorioProduto.findById(bodyAvaliar.idProduto);
        if (optProduto.isEmpty()) {
            return new RetornoBadRequest(response, "O produto de id '" + bodyAvaliar.idProduto + "' nao existe!");
        }
        Produto produto = optProduto.get();

        Optional<AvaliacaoProduto> optAvaliacao = repositorioAvaliacaoProduto.findByUsuarioEProduto(usuario.getIdUsuario(), produto.getId());
        if (optAvaliacao.isEmpty()) {
            return new RetornoBadRequest(response, "Avaliacao nao encontrada!");
        }
        AvaliacaoProduto avaliacao = optAvaliacao.get();
        repositorioAvaliacaoProduto.deleteById(avaliacao.getId());

        atualizarAvaliacaoProduto(avaliacao.getProduto());
        return new RetornoSucesso(response, avaliacao);
    }

    public static class BodyAvaliar {
        public long idProduto;
        public int avaliacao;
    }

    @PostMapping("/avaliar")
    public Retorno avaliar(@RequestBody BodyAvaliar bodyAvaliar, HttpServletRequest request, HttpServletResponse response) {
        Optional<Usuario> optUsuario = ControllerUsuario.adquirirUsuario(repositorioSessao, request.getSession());
        if (optUsuario.isEmpty()) {
            return new RetornoUnauthorized(response);
        }
        Usuario usuario = optUsuario.get();

        if (bodyAvaliar.avaliacao > 5 || bodyAvaliar.avaliacao < 0) {
            return new RetornoBadRequest(response, "A avaliacao somente pode conter numeros entre 0 e 5.");
        }

        Optional<Produto> optProduto = repositorioProduto.findById(bodyAvaliar.idProduto);
        if (optProduto.isEmpty()) {
            return new RetornoBadRequest(response, "O produto de id '" + bodyAvaliar.idProduto + "' nao existe!");
        }
        Produto produto = optProduto.get();

        Optional<AvaliacaoProduto> optAvaliacao = repositorioAvaliacaoProduto.findByUsuarioEProduto(usuario.getIdUsuario(), bodyAvaliar.idProduto);
        AvaliacaoProduto avaliacao = null;
        if (optAvaliacao.isPresent()) {
            avaliacao = optAvaliacao.get();
            avaliacao.setAvaliacao(bodyAvaliar.avaliacao);
            repositorioAvaliacaoProduto.save(avaliacao);
        } else {
            avaliacao = new AvaliacaoProduto();
            avaliacao.setProduto(produto);
            avaliacao.setUsuario(usuario);
            avaliacao.setAvaliacao(bodyAvaliar.avaliacao);
            repositorioAvaliacaoProduto.save(avaliacao);
        }

        atualizarAvaliacaoProduto(produto);

        return new RetornoSucesso(response, avaliacao);
    }

    private void atualizarAvaliacaoProduto(Produto produto) {
        Set<AvaliacaoProduto> avaliacoes = repositorioAvaliacaoProduto.findByProduto(produto.getId());
        produto.setAvaliacao(0.0);
        for (AvaliacaoProduto avaliacao: avaliacoes) {
            produto.setAvaliacao(produto.getAvaliacao() + avaliacao.getAvaliacao());
        }
        if (!avaliacoes.isEmpty()) {
            produto.setAvaliacao(produto.getAvaliacao() / avaliacoes.size());
        }
        produto.setNumAvaliacoes(avaliacoes.size());
        repositorioProduto.save(produto);
    }
}

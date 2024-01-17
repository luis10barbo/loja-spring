package com.luisbb.loja.springboot.rest;

import com.luisbb.loja.model.retorno.Retorno;
import com.luisbb.loja.model.retorno.RetornoSucesso;
import com.luisbb.loja.model.retorno.RetornoUnauthorized;
import com.luisbb.loja.springboot.jpa.entidades.Transportadora;
import com.luisbb.loja.springboot.jpa.entidades.Usuario;
import com.luisbb.loja.springboot.jpa.repositorios.RepositorioSessao;
import com.luisbb.loja.springboot.jpa.repositorios.RepositorioTransportadora;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/transportadoras")
public class ControllerTransportadora {

    private final RepositorioTransportadora repositorioTransportadora;
    private final RepositorioSessao repositorioSessao;

    public ControllerTransportadora(RepositorioTransportadora repositorioTransportadora, RepositorioSessao repositorioSessao) {
        this.repositorioTransportadora = repositorioTransportadora;
        this.repositorioSessao = repositorioSessao;
    }
    @GetMapping("/todas")
    public Iterable<Transportadora> getTransportadoras() {
        return repositorioTransportadora.findAll();
    }

    @PostMapping("/")
    public Retorno criarTransportadora(@RequestBody Transportadora transportadora, HttpServletRequest request, HttpServletResponse response) {
        Optional<Usuario> optUsuario = ControllerUsuario.adquirirUsuario(repositorioSessao, request.getSession());
        if (optUsuario.isEmpty()) {
            return new RetornoUnauthorized(response, "Voce precisa estar logado para criar um frete");
        }

        if (!optUsuario.get().isAdmin()) {
            return new RetornoUnauthorized(response, "Voce nao tem permissao para criar um frete");
        }

        return new RetornoSucesso(response, repositorioTransportadora.save(transportadora));
    }
}

package com.luisbb.loja.springboot.rest;

import com.luisbb.loja.model.retorno.*;
import com.luisbb.loja.springboot.jpa.entidades.*;
import com.luisbb.loja.springboot.jpa.repositorios.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    @PostMapping("/criar")
    public Ordem criar(HttpServletRequest request, @RequestBody long idTransportadora) {
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

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.HOUR_OF_DAY, 72);
        ordem.setMomentoEsperadoFinalizada(cal.getTime());
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

    @GetMapping("/todas")
    public Retorno todasOrdens(HttpServletRequest request, HttpServletResponse resposta) {
        Optional<Usuario> optUsuario = ControllerUsuario.adquirirUsuario(repositorioSessao, request.getSession());
        if (optUsuario.isEmpty()) {
            return null;
        }
        Usuario usuario = optUsuario.get();

        Optional<Set<Ordem>> optOrdens = repositorioOrdem.findByUserId(usuario.getIdUsuario());
        if (optOrdens.isEmpty()) {
            return new RetornoNotFound(resposta, "Nenhuma ordem encontrada");
        }
        Set<Ordem> ordens = optOrdens.get();

        checarEFinalizarOrdensViaMomentoEsperado(ordens);


        return new RetornoSucesso(resposta, ordens);
    }

    void checarEFinalizarOrdensViaMomentoEsperado(Set<Ordem> ordens) {
        for (Ordem ordem: ordens) {
            if (ordem.getMomentoEsperadoFinalizada().getTime() < new Date().getTime()) {
                // TODO: implementar outra logica de finalizacao
                // Passou do momento esperado finalizacao
                ordem.setMomentoFinalizada(ordem.getMomentoEsperadoFinalizada());
                repositorioOrdem.save(ordem);
            }
        }
    }

    @PostMapping("/finalizar")
    public boolean finalizarOrdem(HttpServletRequest request, @RequestBody long idOrdem) {
        Optional<Ordem> optOrdem = this.repositorioOrdem.findById(idOrdem);
        if (optOrdem.isEmpty()) {
            return false;
        }
        Ordem ordem = optOrdem.get();
        ordem.setMomentoFinalizada(new Date());
        repositorioOrdem.save(ordem);
        return true;
    }

    @GetMapping
    public Retorno adquirir(HttpServletResponse response, HttpServletRequest request, @RequestParam long idOrdem) {
        Optional<Usuario> optUsuario = ControllerUsuario.adquirirUsuario(repositorioSessao, request.getSession());
        if (optUsuario.isEmpty()) {
            return new RetornoUnauthorized(response, "Voce precisa estar logado para acessar essa pagina");
        }
        Usuario usuario = optUsuario.get();

        Optional<Ordem> optOrdem = repositorioOrdem.findByIdAndUserId(idOrdem, usuario.getIdUsuario());
        if (optOrdem.isEmpty()) {
            return new RetornoNotFound(response, "Ordem nao encontrada");
        }
        Ordem ordem = optOrdem.get();
        return new RetornoSucesso(response, ordem);
    }
}

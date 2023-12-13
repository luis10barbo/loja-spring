package com.luisbb.loja.springboot.rest;

import com.luisbb.loja.springboot.jpa.entidades.Transportadora;
import com.luisbb.loja.springboot.jpa.repositorios.RepositorioTransportadora;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transportadoras")
public class ControllerTransportadora {

    private final RepositorioTransportadora repositorioTransportadora;

    public ControllerTransportadora(RepositorioTransportadora repositorioTransportadora) {
        this.repositorioTransportadora = repositorioTransportadora;
    }
    @GetMapping("/todas")
    public Iterable<Transportadora> getTransportadoras() {
        return repositorioTransportadora.findAll();
    }

    @PostMapping("/")
    public Transportadora criarTransportadora(@RequestBody Transportadora transportadora) {
        return repositorioTransportadora.save(transportadora);
    }
}

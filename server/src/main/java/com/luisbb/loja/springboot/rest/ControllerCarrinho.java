package com.luisbb.loja.springboot.rest;

import com.luisbb.loja.springboot.jpa.entidades.Produto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/carrinhos")
public class ControllerCarrinho {
    @PostMapping("/add")
    public void adicionarProduto(@RequestBody Produto produto) {

    }
}

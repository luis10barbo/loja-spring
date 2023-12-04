package com.luisbb.loja.springboot.rest;

import com.luisbb.loja.springboot.jpa.entidades.Usuario;
import com.luisbb.loja.springboot.jpa.repositorios.RepositorioUsuario;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class ControllerUsuario {
    private final RepositorioUsuario repositorioUsuario;
    public ControllerUsuario(RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    @GetMapping("/all")
    public Iterable<Usuario> adquirirUsuarios() {
        return this.repositorioUsuario.findAll();
    }

    @GetMapping("/")
    public Usuario adquirirUsuario(@RequestParam long id) {
        return this.repositorioUsuario.findById(id);
    }

    @PostMapping("/")
    public Usuario adicionarUsuario(@RequestBody Usuario usuario) {
        return this.repositorioUsuario.save(usuario);
    }

    @DeleteMapping("/")
    public void removerUsuario(long id) {
        this.repositorioUsuario.deleteById(id);
    }
}

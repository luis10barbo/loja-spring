package com.luisbb.loja.springboot.rest;

import com.luisbb.loja.springboot.jpa.entidades.Usuario;
import com.luisbb.loja.springboot.jpa.repositorios.RepositorioUsuario;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class ControllerUsuario {
    private static final String NOME_COOKIE = "idUsuario";
    private final RepositorioUsuario repositorioUsuario;
    public ControllerUsuario(RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    @GetMapping("/all")
    public Iterable<Usuario> adquirirUsuarios() {
        return this.repositorioUsuario.findAll();
    }

    @GetMapping("/")
    public Optional<Usuario> adquirirUsuario(@RequestParam long id) {
        return this.repositorioUsuario.findById(id);
    }

    @PostMapping("/")
    public Usuario adicionarUsuario(@RequestBody Usuario usuario) {
        return this.repositorioUsuario.save(usuario);
    }

    @Getter
    @Setter
    static
    class CorpoLogin {
        private String apelido;
        private String senha;
    }

    @GetMapping("/eu")
    public Optional<Usuario> eu(HttpServletRequest request) {
        return adquirirUsuario(request.getCookies());
    }

    @PostMapping("/registrar")
    public Optional<Usuario> registrar(HttpServletRequest request, HttpServletResponse resposta, @RequestBody CorpoLogin corpoLogin) {
        if (repositorioUsuario.findByApelido(corpoLogin.getApelido()).isPresent()) {
            resposta.setStatus(409);
            return Optional.empty();
        }

        Usuario usuario = new Usuario();
        usuario.setApelido(corpoLogin.getApelido());
        usuario.setSaltSenha(BCrypt.gensalt(10));
        usuario.setHashSenha(BCrypt.hashpw(corpoLogin.senha, usuario.getSaltSenha()));
        usuario = repositorioUsuario.save(usuario);

        resposta.addCookie(criarCookie(usuario));
        return Optional.of(usuario);
    }

    @PostMapping("/logar")
    public Optional<Usuario> logar(HttpServletRequest request, HttpServletResponse resposta, @RequestBody CorpoLogin corpoLogin) {
        Optional<Usuario> optUsuario = repositorioUsuario.findByApelido(corpoLogin.getApelido());
        if (optUsuario.isEmpty()) {
            return optUsuario;
        }
        Usuario usuario = optUsuario.get();
        if (!BCrypt.checkpw(corpoLogin.getSenha(), usuario.getHashSenha())) {
            return Optional.empty();
        };

        resposta.addCookie(criarCookie(usuario));
        return Optional.of(usuario);
    }

    @DeleteMapping("/")
    public void removerUsuario(long id) {
        this.repositorioUsuario.deleteById(id);
    }

    private Cookie criarCookie(Usuario usuario) {
        Cookie jwtTokenCookie = new Cookie(NOME_COOKIE, usuario.getIdUsuario().toString());
        jwtTokenCookie.setMaxAge(86400);
        jwtTokenCookie.setHttpOnly(true);
        return jwtTokenCookie;
    }

    private Optional<Usuario> adquirirUsuario(Cookie[] cookies) {
        if (cookies == null) return Optional.empty();
        Optional<String> optIdUsuario = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals(NOME_COOKIE)).map(Cookie::getValue).findFirst();
        return optIdUsuario.flatMap(idUsuario -> repositorioUsuario.findById(Long.parseLong(idUsuario)));

    }
}

package com.luisbb.loja.springboot.rest;

import com.luisbb.loja.springboot.jpa.entidades.Carrinho;
import com.luisbb.loja.springboot.jpa.entidades.Sessao;
import com.luisbb.loja.springboot.jpa.entidades.Usuario;
import com.luisbb.loja.springboot.jpa.repositorios.RepositorioCarrinho;
import com.luisbb.loja.springboot.jpa.repositorios.RepositorioSessao;
import com.luisbb.loja.springboot.jpa.repositorios.RepositorioUsuario;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuarios")
public class ControllerUsuario {
    private static final String NOME_COOKIE = "idUsuario";
    private final RepositorioUsuario repositorioUsuario;
    private final RepositorioCarrinho repositorioCarrinho;
    private final RepositorioSessao repositorioSessao;

    public ControllerUsuario(RepositorioUsuario repositorioUsuario, RepositorioCarrinho repositorioCarrinho, RepositorioSessao repositorioSessao) {
        this.repositorioUsuario = repositorioUsuario;
        this.repositorioCarrinho = repositorioCarrinho;
        this.repositorioSessao = repositorioSessao;
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
        return adquirirUsuario(repositorioSessao, request.getSession());
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

//        Carrinho carrinho = new Carrinho();
//        carrinho.setUsuario(usuario);
//        repositorioCarrinho.save(carrinho);

        adicionarUsuarioSessao(request.getSession(), usuario);
//        resposta.addCookie(criarCookie(usuario));
        return Optional.of(usuario);
    }

    @PostMapping("/entrar")
    public Optional<Usuario> logar(HttpServletRequest request, HttpServletResponse resposta, @RequestBody CorpoLogin corpoLogin) {
        Optional<Usuario> optUsuario = repositorioUsuario.findByApelido(corpoLogin.getApelido());
        if (optUsuario.isEmpty()) {
            return optUsuario;
        }
        Usuario usuario = optUsuario.get();
        if (!BCrypt.checkpw(corpoLogin.getSenha(), usuario.getHashSenha())) {
            return Optional.empty();
        };

        adicionarUsuarioSessao(request.getSession(), usuario);
//        resposta.addCookie(criarCookie(usuario));
        return Optional.of(usuario);
    }

    @DeleteMapping("/")
    public void removerUsuario(long id) {
        this.repositorioUsuario.deleteById(id);
    }

    @PostMapping("/sair")
    public void sairUsuario(HttpServletRequest request) {
        Optional<Usuario> optUsuario = adquirirUsuario(repositorioSessao, request.getSession());
        if (optUsuario.isEmpty()) {
            return;
        }

        request.getSession().invalidate();
    }

    private Cookie removerCookie() {
        Cookie jwtTokenCookie = new Cookie(NOME_COOKIE, null);
        jwtTokenCookie.setMaxAge(86400);
        jwtTokenCookie.setHttpOnly(true);
        jwtTokenCookie.setPath("/");
        return jwtTokenCookie;
    }

    private Cookie criarCookie(Usuario usuario) {
        Cookie jwtTokenCookie = new Cookie(NOME_COOKIE, usuario.getIdUsuario().toString());
        jwtTokenCookie.setMaxAge(86400);
        jwtTokenCookie.setHttpOnly(true);
        jwtTokenCookie.setPath("/");
        return jwtTokenCookie;
    }

    private void adicionarUsuarioSessao(HttpSession sessaoHttp, Usuario usuario) {
        sessaoHttp.setAttribute(NOME_COOKIE, usuario);
        Sessao sessao = new Sessao();
        sessao.setId(sessaoHttp.getId());
        sessao.setUsuario(usuario);
        repositorioSessao.save(sessao);
    }

    public static Optional<Usuario> adquirirUsuarioCookie(RepositorioUsuario repositorioUsuario, Cookie[] cookies) {
        if (cookies == null) return Optional.empty();
        Optional<String> optIdUsuario = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals(NOME_COOKIE)).map(Cookie::getValue).findFirst();
        return optIdUsuario.flatMap(idUsuario -> repositorioUsuario.findById(Long.parseLong(idUsuario)));
    }

    private void removerCookie(Cookie cookie) {}

    public static Optional<Usuario> adquirirUsuario(RepositorioSessao repositorioSessao, HttpSession sessaoHttp) {
        if (sessaoHttp == null) return Optional.empty();
//        Optional<String> optIdUsuario = Arrays.stream(cookies).filter(cookie -> cookie.getName().equals(NOME_COOKIE)).map(Cookie::getValue).findFirst();

//        Usuario usuario = (Usuario) sessaoHttp.getAttribute(NOME_COOKIE);
//        if (usuario == null) {
        Optional<Sessao> optSessao = repositorioSessao.findById(sessaoHttp.getId());
        if (optSessao.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(optSessao.get().getUsuario());
//        };
    }

}

package com.luisbb.loja.model.retorno;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RetornoUnauthorized extends RetornoErro implements Serializable {

    public RetornoUnauthorized(HttpServletResponse response) {
        super(response, "Voce nao esta autorizado a utilizar essa funcionalidade");
    }
    public RetornoUnauthorized(HttpServletResponse response, String erro) {
        super(response, erro);
    }

    @Override
    public int codigoStatus() {
        return 401;
    }
}

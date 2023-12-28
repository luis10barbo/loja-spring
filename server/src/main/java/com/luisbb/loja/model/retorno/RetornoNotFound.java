package com.luisbb.loja.model.retorno;

import jakarta.servlet.http.HttpServletResponse;

public class RetornoNotFound extends RetornoErro {

    public RetornoNotFound(HttpServletResponse response) {
        super(response, "Nao encontrado!");
    }

    public RetornoNotFound(HttpServletResponse response, String erro) {
        super(response, erro);
    }

    @Override
    public int codigoStatus() {
        return 404;
    }
}

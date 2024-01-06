package com.luisbb.loja.model.retorno;

import jakarta.servlet.http.HttpServletResponse;

public class RetornoInternalServerError extends RetornoErro {
    public RetornoInternalServerError(HttpServletResponse response) {
        super(response, "Ocorreu um erro interno no servidor");
    }

    public RetornoInternalServerError(HttpServletResponse response, String erro) {
        super(response, erro);
    }
    @Override
    public int codigoStatus() {
        return 500;
    }
}

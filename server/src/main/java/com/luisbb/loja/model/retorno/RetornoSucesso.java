package com.luisbb.loja.model.retorno;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RetornoSucesso extends Retorno {

    private Object retorno;
    public RetornoSucesso(HttpServletResponse response) {
        super(response);
    }

    public RetornoSucesso(HttpServletResponse response, Object retorno) {
        super(response);
        this.retorno = retorno;
    }

    @Override
    public int codigoStatus() {
        return 200;
    }
}

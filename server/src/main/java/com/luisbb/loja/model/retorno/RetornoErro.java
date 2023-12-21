package com.luisbb.loja.model.retorno;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class RetornoErro extends Retorno {
    private String erro;

    public RetornoErro(HttpServletResponse response, String erro) {
        super(response);
        this.erro = erro;
    }
}

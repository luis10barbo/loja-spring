package com.luisbb.loja.model.retorno;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Retorno {
    private int status;
    public Retorno(HttpServletResponse response) {
        status = codigoStatus();
        response.setStatus(status);
    }
     public abstract int codigoStatus();
}

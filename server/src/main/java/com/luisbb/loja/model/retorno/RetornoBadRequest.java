package com.luisbb.loja.model.retorno;

import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class RetornoBadRequest extends RetornoErro implements Serializable {


    public RetornoBadRequest(HttpServletResponse response, String erro) {
        super(response, erro);

    }

    @Override
    public int codigoStatus() {
        return 400;
    }
}

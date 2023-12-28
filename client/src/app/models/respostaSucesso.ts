import { Resposta } from "./resposta";

export interface RespostaSucesso<Retorno> extends Resposta {
    retorno: Retorno
}
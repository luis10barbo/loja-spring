import { Carrinho } from "./carrinho";

export interface Usuario {
    id?:number;
    apelido: String;
    carrinho: Carrinho;
}
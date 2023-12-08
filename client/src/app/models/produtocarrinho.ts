import { Carrinho } from "./carrinho";
import { Produto } from "./produto";

export interface ProdutoCarrinho {
    produto: Produto;
    carrinho: Carrinho;
    quantidade: number;
}
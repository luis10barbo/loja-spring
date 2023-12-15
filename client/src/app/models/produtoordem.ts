import { Produto } from "./produto";

export interface ProdutoOrdem {
    id: number;
    preco: number;
    quantidade: number;
    produto: Produto;
}
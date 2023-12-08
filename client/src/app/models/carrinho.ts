import { Produto } from "./produto";
import { ProdutoCarrinho } from "./produtocarrinho";
import { Usuario } from "./usuario";

export interface Carrinho {
    id?:number;
    usuario: Usuario;
    produtos: ProdutoCarrinho[];
}

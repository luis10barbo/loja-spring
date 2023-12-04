import { Produto } from "./produto";
import { Usuario } from "./usuario";

export interface Carrinho {
    id?:number;
    usuario: Usuario;
    produtos: Produto[];
}

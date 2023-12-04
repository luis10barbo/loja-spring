import { Produto } from "./produto";
import { Transportadora } from "./transportadora";

export interface Ordem {
    id?: number;
    entregaRealizada: boolean;
    produtos: Produto[];
    transportadora: Transportadora;
}
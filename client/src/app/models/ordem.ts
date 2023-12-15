import { Produto } from "./produto";
import { ProdutoOrdem } from "./produtoordem";
import { Transportadora } from "./transportadora";

export interface Ordem {
    id: number;
    produtosOrdem: ProdutoOrdem[];
    transportadora: Transportadora;
    momentoCriacao: number;
    momentoFinalizada?: number;
    cancelada: boolean;
    frete: number;
}
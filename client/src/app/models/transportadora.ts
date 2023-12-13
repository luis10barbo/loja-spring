import { Ordem } from "./ordem";

export interface Transportadora {
    id?: number;
    nome:String;
    prazoHoras:number;
    valorFrete: number;
    ordens: Ordem[];
}
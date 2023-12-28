import { Resposta } from "./resposta";

export interface RespostaErro extends Resposta {
    status: number;
    erro: String;
}
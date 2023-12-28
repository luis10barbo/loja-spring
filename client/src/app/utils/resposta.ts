import { Resposta } from "../models/resposta";
import { RespostaErro } from "../models/respostaErro";

export function eErroResposta(objeto: Resposta): objeto is RespostaErro {
    if ((objeto as any).erro) {
        return true;
    }
    return false;
} 
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Ordem } from 'src/app/models/ordem';
import { Resposta } from 'src/app/models/resposta';
import { RespostaErro } from 'src/app/models/respostaErro';
import { RespostaSucesso } from 'src/app/models/respostaSucesso';
import { enviroment } from 'src/environment/environment';

@Injectable({
  providedIn: 'root'
})
export class OrdemService {
  adquirirOrdem(idOrdem: number) {
    return this.httpClient.get<RespostaSucesso<Ordem> | RespostaErro>(this.url, {withCredentials: true, params: {idOrdem}});
  }
  
  url = enviroment.urlBackend + "/ordens";

  constructor(private httpClient: HttpClient) { }
  
  criarOrdem(idTransportadora:number) {
    return this.httpClient.post<Ordem>(this.url + "/criar", idTransportadora, {withCredentials: true});
  }

  cancelarOrdem(ordem: Ordem) {
    return this.httpClient.post(this.url + "/cancelar", ordem.id, {withCredentials:true});
  }

  adquirirOrdens() {
    return this.httpClient.get<Ordem[]>(this.url + "/todas", {withCredentials:true});
  }

  finalizarOrdem(idOrdem: number) {
    return this.httpClient.post<boolean>(this.url + "/finalizar", idOrdem, {withCredentials:true});
  }
}

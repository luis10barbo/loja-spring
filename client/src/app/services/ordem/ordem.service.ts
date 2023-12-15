import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Ordem } from 'src/app/models/ordem';
import { enviroment } from 'src/environment/environment';

@Injectable({
  providedIn: 'root'
})
export class OrdemService {
  
  url = enviroment.urlBackend + "/ordens";

  constructor(private httpClient: HttpClient) { }
  
  criarOrdem(idTransportadora:number) {
    return this.httpClient.post(this.url + "/criar", idTransportadora, {withCredentials: true});
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

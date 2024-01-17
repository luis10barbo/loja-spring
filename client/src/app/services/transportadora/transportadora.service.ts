import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RespostaErro } from 'src/app/models/respostaErro';
import { RespostaSucesso } from 'src/app/models/respostaSucesso';
import { Transportadora } from 'src/app/models/transportadora';
import { enviroment } from 'src/environment/environment';

@Injectable({
  providedIn: 'root'
})
export class TransportadoraService {
  url = enviroment.urlBackend + "/transportadoras"

  constructor(private httpClient: HttpClient) { }
  adquirirTodas() {
    return this.httpClient.get<Transportadora[]>(this.url + "/todas");
  }

  criar(transportadora: Transportadora) {
    return this.httpClient.post<RespostaSucesso<Transportadora> | RespostaErro>(this.url + "/", transportadora, {withCredentials: true});
  }
}

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
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

}

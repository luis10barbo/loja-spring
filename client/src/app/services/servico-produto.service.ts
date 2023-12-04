import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Produto } from '../models/produto';
import { HttpClient } from "@angular/common/http"
import { enviroment } from 'src/environment/environment';

@Injectable()
export class ServicoProduto {
  private url = enviroment.urlBackend + "/produtos"
  constructor(private http: HttpClient) { }

  public adquirirTodos(): Observable<Produto[]> {
    return this.http.get<Produto[]>(this.url + "/all");
  }
}

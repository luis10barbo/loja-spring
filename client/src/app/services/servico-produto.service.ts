import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Produto } from '../models/produto';
import { HttpClient } from "@angular/common/http"
import { enviroment } from 'src/environment/environment';
import { Resposta } from '../models/resposta';

@Injectable()
export class ServicoProduto {
  private url = enviroment.urlBackend + "/produtos"
  constructor(private http: HttpClient) { }

  criar(criacaoProduto: Produto) {
    return this.http.post<Resposta>(this.url, criacaoProduto, {withCredentials: true});
  }

  public adquirirTodos(pesquisa?: string): Observable<Produto[]> {
    return this.http.get<Produto[]>(this.url + "/all", {params: {s: pesquisa ? pesquisa : ""}});
  }

  public adquirir(id:number): Observable<Produto> {
    return this.http.get<Produto>(`${this.url}?id=${id}`);
  }
}

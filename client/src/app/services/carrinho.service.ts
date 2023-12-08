import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { enviroment } from 'src/environment/environment';
import { Produto } from '../models/produto';
import { ProdutoCarrinho } from '../models/produtocarrinho';

@Injectable({
  providedIn: 'root'
})
export class CarrinhoService {
  
  url = enviroment.urlBackend + "/carrinhos"
  constructor(private httpClient: HttpClient) { }
  public adicionarAoCarrinho(produto: Produto) {
    return this.httpClient.post<boolean>(this.url + "/add", produto, {withCredentials:true});
  }

  removerDoCarrinho(produto: Produto) {
    return this.httpClient.post<boolean>(this.url + "/remover", produto, {withCredentials:true});
  }

  removerTodoCarrinho() {
    return this.httpClient.post<boolean>(this.url + "/removerTodos", undefined, {withCredentials:true})
  }
}

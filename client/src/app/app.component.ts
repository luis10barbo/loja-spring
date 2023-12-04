import { Component, OnInit } from '@angular/core';
import { Produto } from './models/produto';
import { ServicoProduto } from './services/servico-produto.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'loja generica';
  produtos: Produto[] = [{nome: "Produto Exemplo", avaliacao:4.3, preco:79.99, descricao: ""}];

  constructor(private servicoProduto: ServicoProduto) {}

  ngOnInit(): void {
    this.servicoProduto.adquirirTodos().subscribe(data => {
      this.produtos = data;
    })
  }
}

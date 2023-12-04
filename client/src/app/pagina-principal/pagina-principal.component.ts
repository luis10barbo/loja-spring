import { Component } from '@angular/core';
import { Produto } from '../models/produto';
import { ServicoProduto } from '../services/servico-produto.service';

@Component({
  selector: 'app-pagina-principal',
  templateUrl: './pagina-principal.component.html',
  styleUrls: ['./pagina-principal.component.scss']
})
export class PaginaPrincipalComponent {
  title = 'loja generica';
  produtos: Produto[] = [{nome: "Produto Exemplo", avaliacao:4.3, preco:79.99, descricao: ""}];

  constructor(private servicoProduto: ServicoProduto) {}

  ngOnInit(): void {
    this.servicoProduto.adquirirTodos().subscribe(data => {
      this.produtos = data;
    })
  }
}

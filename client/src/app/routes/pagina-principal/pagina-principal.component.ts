import { Component, ViewChild } from '@angular/core';
import { Produto } from '../../models/produto';
import { ServicoProduto } from '../../services/servico-produto.service';

@Component({
  selector: 'app-pagina-principal',
  templateUrl: './pagina-principal.component.html',
  styleUrls: ['./pagina-principal.component.scss']
})
export class PaginaPrincipalComponent {
  title = 'loja generica';
  produtos: Produto[] = [];

  constructor(private servicoProduto: ServicoProduto) {}
  
  ngOnInit(): void {
    this.servicoProduto.adquirirTodos().subscribe(data => {
      this.produtos = data;
    })
  }
}

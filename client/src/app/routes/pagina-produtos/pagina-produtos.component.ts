import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Produto } from 'src/app/models/produto';
import { ServicoProduto } from 'src/app/services/servico-produto.service';

@Component({
  selector: 'app-pagina-produtos',
  templateUrl: './pagina-produtos.component.html',
  styleUrls: ['./pagina-produtos.component.scss']
})
export class PaginaProdutosComponent implements OnInit {
  produtos?: Produto[];
  pesquisa: string = "";
  quantidadeProdutos: number = 0;
  teste = true;

  constructor(private produtoService: ServicoProduto, private activatedRoute:ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.queryParams.subscribe(params => {
      this.pesquisa = (params as any)["s"];
      this.produtoService.adquirirTodos(this.pesquisa).subscribe(produtos => {
        this.produtos = produtos;
        this.quantidadeProdutos = produtos.length;
      });
    })
  }
}

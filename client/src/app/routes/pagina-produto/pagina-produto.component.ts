import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Produto } from 'src/app/models/produto';
import { ServicoProduto } from 'src/app/services/servico-produto.service';

@Component({
  selector: 'app-pagina-produto',
  templateUrl: './pagina-produto.component.html',
  styleUrls: ['./pagina-produto.component.scss']
})
export class PaginaProdutoComponent implements OnInit {
  produto: Produto | undefined;
  constructor(private route: ActivatedRoute, private servicoProduto: ServicoProduto) {
    
  }
  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.servicoProduto.adquirir(params["id"]).subscribe(data => {
        this.produto = data;
      });
    })
  }
}

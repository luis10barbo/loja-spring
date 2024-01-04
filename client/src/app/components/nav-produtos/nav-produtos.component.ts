import { Component, Input } from '@angular/core';
import { Produto } from 'src/app/models/produto';

@Component({
  selector: 'nav-produtos',
  templateUrl: './nav-produtos.component.html',
  styleUrls: ['./nav-produtos.component.scss']
})
export class NavProdutosComponent {
  @Input()
  produtos!: Produto[];

  @Input()
  clique?: (produto: Produto) => void;

  @Input()
  textoClique: string = "Selecionar";

  @Input()
  tamanho: "P" | "N" = "N";
}

import { Component, EventEmitter, Input, Output } from '@angular/core';
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
  cliqueCustomizado: boolean = false;

  @Input()
  cliquePerigoso: boolean = false;

  @Output()
  aoClicar = new EventEmitter<Produto>();


  @Input()
  textoClique: string = "Selecionar";

  @Input()
  tamanho: "P" | "N" = "N";

  transmitirClique(produto: Produto) {
    this.aoClicar.emit(produto);
  }
}

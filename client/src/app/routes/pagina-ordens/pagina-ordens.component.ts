import { Component, OnInit } from '@angular/core';
import { Ordem } from 'src/app/models/ordem';
import { OrdemService } from 'src/app/services/ordem/ordem.service';
import { eErroResposta } from 'src/app/utils/resposta';

@Component({
  selector: 'app-pagina-ordens',
  templateUrl: './pagina-ordens.component.html',
  styleUrls: ['./pagina-ordens.component.scss']
})
export class PaginaOrdensComponent implements OnInit {

  ordens?: Ordem[];
  extraOrdens: Map<number, {data: Date, total: number, subtotal: number, dataFinalizada?: Date}> = new Map();

  constructor(private ordemService: OrdemService) {}

  ngOnInit(): void {
    this.adquirirOrdens();
  }

  adquirirOrdens() {
    this.ordemService.adquirirOrdens().subscribe(res => {
      if (eErroResposta(res)) {
        return;
      }
      this.ordens = res.retorno;
      this.gerarInfoExtra();
    });
  }

  gerarInfoExtra() {
    this.extraOrdens.clear();
    this.ordens?.forEach((ordem) => {
      if (ordem.id) {
        let subtotal = 0.0;
        ordem.produtosOrdem.forEach((produtoOrdem) => {
          subtotal += produtoOrdem.preco * produtoOrdem.quantidade;
        })

        this.extraOrdens.set(ordem.id, {
          data: new Date(ordem.momentoCriacao),
          subtotal: subtotal,
          total: subtotal + ordem.frete,
          dataFinalizada: ordem.momentoFinalizada ? new Date(ordem.momentoFinalizada) : undefined
        });
      }
    })
  }

  cancelarOrdem(ordem: Ordem) {
    this.ordemService.cancelarOrdem(ordem).subscribe(res => {
      if (res) {
        this.adquirirOrdens();
      }
    });
  }

  finalizarOrdem(ordem: Ordem) {
    this.ordemService.finalizarOrdem(ordem.id).subscribe(res => {
      if (res) {
        this.adquirirOrdens();
      }
    })
  }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Ordem } from 'src/app/models/ordem';
import { Produto } from 'src/app/models/produto';
import { OrdemService } from 'src/app/services/ordem/ordem.service';
import { eErroResposta } from 'src/app/utils/resposta';

@Component({
  selector: 'app-pagina-finalizado',
  templateUrl: './pagina-finalizado.component.html',
  styleUrls: ['./pagina-finalizado.component.scss']
})
export class PaginaFinalizadoComponent implements OnInit{
  ordem?: Ordem;
  produtos?: Produto;
  
  constructor(private ordemService: OrdemService, private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      if ((params as any).ordem) {
        this.ordemService.adquirirOrdem((params as any).ordem).subscribe(ordem => {
          if (eErroResposta(ordem)) {
            return;
          } 
          this.ordem = ordem.retorno;
        });    
      }
    })
  }

}

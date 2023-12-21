import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { Produto } from 'src/app/models/produto';
import { Transportadora } from 'src/app/models/transportadora';
import { Usuario } from 'src/app/models/usuario';
import { CarrinhoService } from 'src/app/services/carrinho.service';
import { OrdemService } from 'src/app/services/ordem/ordem.service';
import { TransportadoraService } from 'src/app/services/transportadora/transportadora.service';
import { UsuarioService } from 'src/app/services/usuario.service';

@Component({
  selector: 'app-pagina-finalizar',
  templateUrl: './pagina-finalizar.component.html',
  styleUrls: ['./pagina-finalizar.component.scss']
})
export class PaginaFinalizarComponent implements OnInit {

  transportadoras?: Transportadora[];
  transportadoraSelecionada?: Transportadora;
  usuario?: Usuario;
  usuarioSub: Subscription | undefined;
  resumo = {
    subtotal: 0.0,
    frete: 0.0,
    total: 0.0
  }
  
  constructor(private ordemService: OrdemService, private usuarioService: UsuarioService, private carrinhoService: CarrinhoService, private transportadoraService: TransportadoraService, private router: Router) {}

  ngOnInit(): void {
    this.usuarioSub = this.usuarioService.adquirirEu().subscribe(usuario => {
      if (!usuario?.carrinho.produtos.length || usuario.carrinho.produtos.length < 1 ) {
        this.usuarioSub?.unsubscribe();
        this.router.navigate(["/"]);
      }
      this.usuario = usuario;
      this.gerarResumo();

    })

    this.transportadoraService.adquirirTodas().subscribe(transportadoras => {
      this.transportadoras = transportadoras;
      this.selecionarTransportadora(transportadoras[0]);
      this.gerarResumo();
    });
  }

  removerProduto(produto: Produto) {
    this.carrinhoService.removerDoCarrinho(produto).subscribe(res => {
      if (res) {
        this.usuarioService.atualizarEu();
      }
    });
  }

  selecionarTransportadora(transportadora?: Transportadora) {
    if (transportadora) {
      this.transportadoraSelecionada = transportadora;
      this.gerarResumo();
    }
  }

  gerarResumo() {
    if (!this.usuario) {
      return;
    }
    
    let subtotal = 0;
    this.usuario.carrinho.produtos.forEach(produtoCarrinho => {
      subtotal += produtoCarrinho.produto.preco * produtoCarrinho.quantidade;
    });
    const frete = this.transportadoraSelecionada ? this.transportadoraSelecionada.valorFrete : 0;  
    const total = subtotal + frete;
    this.resumo = {subtotal, frete, total};
  }

  finalizarOrdem() {
    if (!this.transportadoraSelecionada?.id) {
      // TODO: Mensagem de erro sem transportadora
      return;
    }
    this.ordemService.criarOrdem(this.transportadoraSelecionada.id).subscribe(res => {
      console.log(res);
    });
  }
}

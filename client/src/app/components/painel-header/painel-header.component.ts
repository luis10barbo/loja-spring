import { Component, ElementRef, HostListener, OnInit, ViewChild } from '@angular/core';
import { Produto } from 'src/app/models/produto';
import { Usuario } from 'src/app/models/usuario';
import { CarrinhoService } from 'src/app/services/carrinho.service';
import { UsuarioService } from 'src/app/services/usuario.service';

@Component({
  selector: 'painel-header',
  templateUrl: './painel-header.component.html',
  styleUrls: ['./painel-header.component.scss']
})
export class PainelHeaderComponent implements OnInit {

  usuario: Usuario | undefined;
  carrinhoAberto: boolean = false;
  total: string = "0";
  constructor(private usuarioService: UsuarioService, private carrinhoService: CarrinhoService) {}
  ngOnInit(): void {
    this.usuarioService.adquirirEu().subscribe(data => {
      this.usuario = data;
      this.gerarResumo();
    });
  }

  @ViewChild("painelHeader") painelHeader: ElementRef<HTMLDivElement> | undefined;
  
  @HostListener("window:scroll", ['$event']) 
  onWindowScroll() {
    // do some stuff here when the window is scrolled
    console.log(window.scrollY, this.painelHeader)
    if (!this.painelHeader?.nativeElement.classList) return;

    if (window.scrollY > 60) {
      this.painelHeader.nativeElement.classList.add("scrolled")
    } else {
      this.painelHeader.nativeElement.classList.remove("scrolled")
    }
  }

  toggleCarrinho() {
    this.carrinhoAberto = !this.carrinhoAberto;
  }

  fecharCarrinho() {
    this.carrinhoAberto = false;
  }

  removerDoCarrinho(produto: Produto) {
    this.carrinhoService.removerDoCarrinho(produto).subscribe(res => {
      if (!res) {
        return;
      }
      this.usuarioService.atualizarEu();
    });
  }

  limparCarrinho() {
    this.carrinhoService.removerTodoCarrinho().subscribe(res => {
      if (!res) {
        return;
      }
      this.usuarioService.atualizarEu();
    });
  }

  gerarResumo() {
    if (!this.usuario?.carrinho.produtos) {
      return;
    }
    
    const resumo = {
      total: 0.0,
    };
    for (let i = 0; i < this.usuario.carrinho.produtos.length; i++) {
      const produtoAtual = this.usuario.carrinho.produtos[i];
      resumo.total += produtoAtual.preco * Number.parseInt(produtoAtual.quantidade);
      console.log(produtoAtual)
    }
    
    this.total = resumo.total.toFixed(2);
  }
  atualizarQuantidadeProduto(produto: Produto, evento: FocusEvent) {
    produto.quantidade = (evento.target as HTMLInputElement).value;
    this.gerarResumo();
  }
}

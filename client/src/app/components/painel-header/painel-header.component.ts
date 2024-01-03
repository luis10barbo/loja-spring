import { AfterViewInit, Component, ElementRef, HostListener, Input, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Produto } from 'src/app/models/produto';
import { ProdutoCarrinho } from 'src/app/models/produtocarrinho';
import { Usuario } from 'src/app/models/usuario';
import { CarrinhoService } from 'src/app/services/carrinho.service';
import { UsuarioService } from 'src/app/services/usuario.service';

@Component({
  selector: 'painel-header',
  templateUrl: './painel-header.component.html',
  styleUrls: ['./painel-header.component.scss']
})
export class PainelHeaderComponent implements OnInit, AfterViewInit {

  usuario: Usuario | undefined;
  carrinhoAberto: boolean = false;
  total: string = "0";

  pesquisa: string | null = "";

  atualizarPesquisa(event: any) {
    this.pesquisa = event.target?.value;
  }

  @ViewChild("inputPesquisa") inputPesquisa: ElementRef<HTMLInputElement> | undefined;



  constructor(private usuarioService: UsuarioService, private carrinhoService: CarrinhoService, private activatedRoute: ActivatedRoute) {}
  ngAfterViewInit(): void {
    this.activatedRoute.queryParams.subscribe(params => {
      // console.log(params, this.inputPesquisa);
      const pesquisa = params["s"];
      if (this.inputPesquisa && pesquisa) {
        this.inputPesquisa.nativeElement.value = pesquisa;
      }
    });
  }
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

  removerDoCarrinho(produtoCarrinho: ProdutoCarrinho) {
    this.carrinhoService.removerDoCarrinho(produtoCarrinho.produto).subscribe(res => {
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
      resumo.total += produtoAtual.produto.preco * produtoAtual.quantidade;
      console.log(produtoAtual)
    }

    this.total = resumo.total.toFixed(2);
  }
  atualizarQuantidadeProduto(produto: ProdutoCarrinho, evento: FocusEvent) {
    produto.quantidade = Number.parseInt((evento.target as HTMLInputElement).value);
    this.carrinhoService.atualizarProdutoCarrinho(produto.quantidade).subscribe(res => {
      if (!res) {
        // TODO: Resolver erro ao update carrinho
      }
    });
    this.gerarResumo();
  }
}

import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { Produto } from 'src/app/models/produto';
import { Usuario } from 'src/app/models/usuario';
import { CarrinhoService } from 'src/app/services/carrinho.service';
import { ServicoProduto } from 'src/app/services/servico-produto.service';
import { UsuarioService } from 'src/app/services/usuario.service';

@Component({
  selector: 'app-pagina-produto',
  templateUrl: './pagina-produto.component.html',
  styleUrls: ['./pagina-produto.component.scss']
})
export class PaginaProdutoComponent implements OnInit {
  produto: Produto | undefined;
  usuario?: Usuario;
  estaEmCarrinho: boolean = false;
  idProduto: number | undefined;
  constructor(private route: ActivatedRoute, private servicoProduto: ServicoProduto, private carrinhoService: CarrinhoService, private usuarioService: UsuarioService) {
  }
  ngOnInit(): void {
    this.route.queryParams.subscribe(params => {
      this.idProduto = params["id"];
      this.adquirirProduto();
    })
  }

  private atualizarUsuario() {
    this.usuarioService.adquirirEu().subscribe(data => {
      this.usuario = data
      console.log(this.usuario, this.produto);
      if (this.usuario?.carrinho.produtos.find(produto => {
        return produto.id === this.produto?.id;
      })) {
        this.estaEmCarrinho = true;
      } else {
        this.estaEmCarrinho = false;
      }
    });
  }

  private adquirirProduto() {
    if (!this.idProduto) {
      return;
    }

    this.servicoProduto.adquirir(this.idProduto).subscribe(data => {
      this.produto = data;
      this.atualizarUsuario();
    });
  }
  public adicionarProdutoCarrinho() {
    if (!this.produto) {
      return;
    }
    this.carrinhoService.adicionarAoCarrinho(this.produto).subscribe(res => {
      if (res && this.produto?.id) {
        this.usuarioService.atualizarEu();
      }
    });
  }

  public removerProdutoCarrinho() {
    if (!this.produto) {
      return;
    }
    this.carrinhoService.removerDoCarrinho(this.produto).subscribe(res => {
      if (res && this.produto?.id) {
        // this.atualizarUsuario();
        this.usuarioService.atualizarEu();
      }
    })
  }

}

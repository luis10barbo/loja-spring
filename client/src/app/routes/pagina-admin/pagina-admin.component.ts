import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Produto } from 'src/app/models/produto';
import { ServicoProduto } from 'src/app/services/servico-produto.service';

const Controle = {
  CriarProduto: "CriarProduto",
  EditarProduto: "EditarProduto"
} as const;

@Component({
  selector: 'app-pagina-admin',
  templateUrl: './pagina-admin.component.html',
  styleUrls: ['./pagina-admin.component.scss']
})
export class PaginaAdminComponent implements OnInit {
  readonly Controle = Controle;
  selecao: keyof typeof Controle = Controle.CriarProduto;
  criacaoProduto: Produto = {
    nome: "",
    avaliacao: 0,
    descricao: "",
    imagem: "",
    numAvaliacoes: 0,
    preco: 0.0,
  };

  edicaoProduto?: Produto;

  constructor(private produtoService: ServicoProduto) {}
  ngOnInit(): void {
    this.pesquisarProdutos("");
  }

  @ViewChild("nomeProduto") inputNomeProduto!: ElementRef<HTMLInputElement>;
  @ViewChild("descricaoProduto") inputDescricaoProduto!: ElementRef<HTMLInputElement>;
  @ViewChild("precoProduto") inputPrecoProduto!: ElementRef<HTMLInputElement>;
  @ViewChild("previewImgProduto") inputImgProduto: ElementRef<HTMLImageElement> | undefined;

  mudarControle(controle: keyof typeof Controle) {
    this.selecao = controle;
  }

  adquirirImagem(event: FocusEvent) {
    const value = (event.target as HTMLInputElement).value;

    if (this.selecao === Controle.CriarProduto) {
      this.criacaoProduto.imagem = value;
      if (this.inputImgProduto && this.criacaoProduto.imagem && this.criacaoProduto.imagem.length !== 0) {
        this.inputImgProduto.nativeElement.src = this.criacaoProduto.imagem;
      }
    }
  }


  criarProduto() {
    this.criacaoProduto.nome = this.inputNomeProduto.nativeElement.value;
    this.criacaoProduto.descricao = this.inputDescricaoProduto.nativeElement.value;
    this.criacaoProduto.preco = Number.parseFloat(this.inputPrecoProduto.nativeElement.value);
    if (Number.isNaN(this.criacaoProduto.preco)) {
      this.criacaoProduto.preco = 0.0;
    }

    this.produtoService.criar(this.criacaoProduto).subscribe();
  }
  @ViewChild("inputPesquisaEditarProduto") inputPesquisaEditarProduto!: ElementRef<HTMLInputElement>;
  produtosEditar!: Produto[];
  pesquisarProdutos(pesquisa: string | MouseEvent) {
    let pesquisaTexto = "";
    if (typeof pesquisa == "string") {
      pesquisaTexto = pesquisa;
    } else {
      pesquisaTexto = this.inputPesquisaEditarProduto.nativeElement.value;
    }

    this.produtoService.adquirirTodos(pesquisaTexto).subscribe(res => {
      this.produtosEditar = res;
    })
  }

  selecionarProdutoEditar(produto: Produto) {
    this.edicaoProduto = produto;
    console.log(this.edicaoProduto);
  }
}

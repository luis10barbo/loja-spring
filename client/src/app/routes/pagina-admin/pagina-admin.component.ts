import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { Produto } from 'src/app/models/produto';
import { Transportadora } from 'src/app/models/transportadora';
import { ServicoProduto } from 'src/app/services/servico-produto.service';
import { TransportadoraService } from 'src/app/services/transportadora/transportadora.service';
import { eErroResposta } from 'src/app/utils/resposta';


const Controle = {
  CriarProduto: "CriarProduto",
  EditarProduto: "EditarProduto",
  RemoverProduto: "RemoverProduto",
  CriarFrete: "CriarFrete"
} as const;

@Component({
  selector: 'app-pagina-admin',
  templateUrl: './pagina-admin.component.html',
  styleUrls: ['./pagina-admin.component.scss']
})
export class PaginaAdminComponent implements OnInit {
  readonly Controle = Controle;
  selecao: keyof typeof Controle = Controle.EditarProduto;
  criacaoProduto: Produto = {
    nome: "",
    avaliacao: 0,
    descricao: "",
    imagem: "",
    numAvaliacoes: 0,
    preco: 0.0,
  };

  edicaoProduto?: Produto;
  edicaoProdutoInicial?: Produto;

  srcImgCriar: string = "";
  srcImgEdit: string = "";

  pesquisaProdutos:string = "";

  constructor(private produtoService: ServicoProduto, private router: Router, private transportadoraService: TransportadoraService) {}
  ngOnInit(): void {
    this.pesquisarProdutos("");
  }

  @ViewChild("nomeProduto") inputNomeProduto!: ElementRef<HTMLInputElement>;
  @ViewChild("descricaoProduto") inputDescricaoProduto!: ElementRef<HTMLInputElement>;
  @ViewChild("precoProduto") inputPrecoProduto!: ElementRef<HTMLInputElement>;
  @ViewChild("imgProduto") inputImgProduto!: ElementRef<HTMLInputElement>;

  @ViewChild("previewImgProduto") imgProduto: ElementRef<HTMLImageElement> | undefined;

  mudarControle(controle: keyof typeof Controle) {
    this.selecao = controle;
  }

  adquirirImagem(event: FocusEvent) {
    const value = (event.target as HTMLInputElement).value;

    this.srcImgCriar = value;
    // if (this.selecao === Controle.CriarProduto) {
    //   this.criacaoProduto.imagem = value;
    //   if (this.inputImgProduto && this.criacaoProduto.imagem && this.criacaoProduto.imagem.length !== 0) {
    //     console.log(this.inputImgProduto, this.editImgProduto);
    //     this.inputImgProduto.nativeElement.src = this.criacaoProduto.imagem;
    //   }
    // }
  }


  criarProduto() {
    this.criacaoProduto.nome = this.inputNomeProduto.nativeElement.value;
    this.criacaoProduto.descricao = this.inputDescricaoProduto.nativeElement.value;
    this.criacaoProduto.preco = Number.parseFloat(this.inputPrecoProduto.nativeElement.value);
    this.criacaoProduto.imagem = this.srcImgCriar;
    if (Number.isNaN(this.criacaoProduto.preco)) {
      this.criacaoProduto.preco = 0.0;
    }
    console.log(this.criacaoProduto);
    // this.produtoService.criar(this.criacaoProduto).subscribe();
  }
  @ViewChild("inputPesquisaEditarProduto") inputPesquisaEditarProduto!: ElementRef<HTMLInputElement>;
  produtosEditar!: Produto[];
  pesquisarProdutos(pesquisa: string | MouseEvent) {
    if (typeof pesquisa == "string") {
      this.pesquisaProdutos = pesquisa;
    } else {
      this.pesquisaProdutos = this.inputPesquisaEditarProduto.nativeElement.value;
    }

    this.produtoService.adquirirTodos(this.pesquisaProdutos).subscribe(res => {
      this.produtosEditar = res;
    })
  }

  @ViewChild("editNomeProduto") inputEditNomeProduto!: ElementRef<HTMLInputElement>;
  @ViewChild("editDescricaoProduto") inputEditDescricaoProduto!: ElementRef<HTMLInputElement>;
  @ViewChild("editPrecoProduto") inputEditPrecoProduto!: ElementRef<HTMLInputElement>;
  @ViewChild("editImgProduto") inputEditImgProduto!: ElementRef<HTMLInputElement>;

  @ViewChild("editPreviewImgProduto") editImgProduto: ElementRef<HTMLImageElement> | undefined;

  selecionarProdutoEditar(produto: Produto) {
    this.edicaoProduto = {...produto};
    this.edicaoProdutoInicial = {...produto};
    if (!this.edicaoProduto.imagem) {
      this.edicaoProduto.imagem = "";
    }
    this.adquirirImagemEdit(this.edicaoProduto.imagem);

  }

  deselecionarProduto() {
    this.edicaoProduto = undefined;
    this.edicaoProdutoInicial = undefined;
  }

  adquirirImagemEdit(img: FocusEvent | string) {
    let valorImg = "";
    if (typeof img === "string") {
      valorImg = img;
    } else {
      valorImg = (img.target as HTMLInputElement).value;
    }
    this.srcImgEdit = valorImg;
    // if (this.selecao === Controle.EditarProduto && this.edicaoProduto) {
    //   this.edicaoProduto.imagem = valorImg;
    //   if (this.editImgProduto && this.edicaoProduto.imagem && this.edicaoProduto.imagem.length !== 0) {
    //     this.editImgProduto.nativeElement.src = this.criacaoProduto.imagem;
    //   }
    // }
    console.log(this.editImgProduto);

  }

  @ViewChild("editNomeProduto")
  set observar(tabEdit: ElementRef) {
    if (tabEdit && this.edicaoProduto) {
      this.inputEditNomeProduto.nativeElement.value = this.edicaoProduto.nome;
      this.inputEditDescricaoProduto.nativeElement.value = this.edicaoProduto.descricao;
      this.inputEditPrecoProduto.nativeElement.value = this.edicaoProduto.preco.toFixed(2);
      if (this.inputEditImgProduto) {
        this.inputEditImgProduto.nativeElement.value = this.edicaoProduto.imagem;
      }
    }
  }

  resetarEdicao() {
    if (this.edicaoProdutoInicial) {
      this.edicaoProduto = {...this.edicaoProdutoInicial};
    }
  }

  editarProduto() {
    if (this.edicaoProduto) {
      this.edicaoProduto.nome = this.inputEditNomeProduto.nativeElement.value;
      this.edicaoProduto.descricao = this.inputEditDescricaoProduto.nativeElement.value;
      this.edicaoProduto.imagem = this.srcImgEdit;
      this.edicaoProduto.preco = Number.parseFloat(this.inputEditPrecoProduto.nativeElement.value);
      if (Number.isNaN(this.edicaoProduto.preco) && this.edicaoProdutoInicial) {
        this.edicaoProduto.preco = this.edicaoProdutoInicial.preco;
      }
      this.produtoService.editar(this.edicaoProduto).subscribe(res => {
        if (eErroResposta(res)) {
          // TODO: Mostrar caixinha de erro
          return;
        }
        this.edicaoProduto = undefined;
        this.edicaoProdutoInicial = undefined;
        this.pesquisarProdutos(this.pesquisaProdutos);
      });
    }
  }
  removerProduto(produto: Produto) {
    this.produtoService.remover(produto).subscribe(res => {
      if (eErroResposta(res)) {
        // TODO: Mostrar caixinha de erro
        return;
      }
      this.pesquisarProdutos(this.pesquisaProdutos);
    }) ;
  }

  novoFrete: Transportadora = {
    nome: "",
    prazoHoras: 24,
    valorFrete: 0.0,
    ordens: []
  }

  @ViewChild("nomeFrete")
  inputNomeFrete!: ElementRef<HTMLInputElement>;

  @ViewChild("prazoHorasFrete")
  inputPrazoHoraFrete!: ElementRef<HTMLInputElement>;

  @ViewChild("valorFrete")
  inputValorFrete!: ElementRef<HTMLInputElement>;

  criarFrete() {

    this.novoFrete.nome = this.inputNomeFrete.nativeElement.value;

    const prazoNovo = parseInt(this.inputPrazoHoraFrete.nativeElement.value);
    if (Number.isNaN(prazoNovo)) {
      return;
    }
    this.novoFrete.prazoHoras = prazoNovo;


    const valorNovo = parseFloat(this.inputValorFrete.nativeElement.value);
    if (Number.isNaN(valorNovo)) {
      return;
    }
    this.novoFrete.valorFrete = valorNovo;

    console.log(this.novoFrete);
    this.transportadoraService.criar(this.novoFrete).subscribe();
  }
}

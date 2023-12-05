import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { Usuario } from 'src/app/models/usuario';
import { UsuarioService } from 'src/app/services/usuario.service';

@Component({
  selector: 'app-pagina-login',
  templateUrl: './pagina-login.component.html',
  styleUrls: ['./pagina-login.component.scss']
})
export class PaginaLoginComponent implements OnInit {
  @ViewChild("inputApelido") inputApelido?: ElementRef<HTMLInputElement>;
  @ViewChild("inputSenha1") inputSenha1?: ElementRef<HTMLInputElement>;
  @ViewChild("inputSenha2") inputSenha2?: ElementRef<HTMLInputElement>;

  usuario: Usuario | undefined;
  tipo: "Registrar" | "Entrar" = "Entrar";

  constructor(private usuarioService: UsuarioService, private router: Router) {}
  ngOnInit(): void {
    if (window.location.href.includes("entrar")) {
      this.tipo = "Entrar";
    } else if (window.location.href.includes("registrar")) {
      this.tipo = "Registrar";
    }

    this.usuarioService.adquirirEu().subscribe(data => {
      if (data) {
        this.router.navigate(["/"])
      }
    })
  }

  // TODO: Adicionar erros
  public logar() {
    if (!this.inputApelido || !this.inputSenha1) {
      return;
    }
    const apelido = this.inputApelido.nativeElement.value;
    const senha = this.inputSenha1.nativeElement.value;
    this.usuarioService.entrar(apelido, senha).subscribe(data => {
      if (data?.id != 0) {
        this.sairPagina()
      }
    });
  }

  // TODO: Adicionar erros
  public registrar() {
    if (!this.inputApelido || !this.inputSenha1 || !this.inputSenha2) {
      return;
    }

    const apelido = this.inputApelido.nativeElement.value;
    const senha = this.inputSenha1.nativeElement.value;
    const senha2 = this.inputSenha2.nativeElement.value;

    if (senha !== senha2) {
      return
    }

    this.usuarioService.registrar(apelido, senha).subscribe(data => {
      if (data?.id != 0) {
        this.sairPagina();
      }
    });
  }

  public sairPagina() {
    this.router.navigate(["/"]);
  }
}

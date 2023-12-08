import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UsuarioService } from 'src/app/services/usuario.service';

@Component({
  selector: 'app-pagina-sair',
  templateUrl: './pagina-sair.component.html',
  styleUrls: ['./pagina-sair.component.scss']
})
export class PaginaSairComponent implements OnInit{
  constructor(private usuarioService: UsuarioService, private router: Router) {
  }
  ngOnInit(): void {
    this.usuarioService.sair().subscribe(() => {
      this.router.navigate(["/"]);
      this.usuarioService.atualizarEu();
    });
  }
}

import { Component, ElementRef, HostListener, OnInit, ViewChild } from '@angular/core';
import { Usuario } from 'src/app/models/usuario';
import { UsuarioService } from 'src/app/services/usuario.service';

@Component({
  selector: 'painel-header',
  templateUrl: './painel-header.component.html',
  styleUrls: ['./painel-header.component.scss']
})
export class PainelHeaderComponent implements OnInit {
  usuario: Usuario | undefined;

  constructor(private usuarioService: UsuarioService) {}
  ngOnInit(): void {
    this.usuarioService.adquirirEu().subscribe(data => {
      this.usuario = data;
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
}

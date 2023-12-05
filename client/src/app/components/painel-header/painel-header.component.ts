import { Component, HostListener, ViewChild } from '@angular/core';

@Component({
  selector: 'painel-header',
  templateUrl: './painel-header.component.html',
  styleUrls: ['./painel-header.component.scss']
})
export class PainelHeaderComponent {
  @ViewChild("painelHeader") painelHeader: {nativeElement: HTMLDivElement} | undefined;
  @HostListener("window:scroll", []) onWindowScroll() {
    // do some stuff here when the window is scrolled
    if (!this.painelHeader?.nativeElement.classList) return;

    if (window.scrollY > 60) {
      this.painelHeader.nativeElement.classList.add("scrolled")
    } else {
      this.painelHeader.nativeElement.classList.remove("scrolled")
    }
}
}

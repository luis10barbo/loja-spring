import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ServicoProduto } from './services/servico-produto.service';
import { HttpClient, HttpClientModule, HttpHandler } from '@angular/common/http';
import { PaginaProdutoComponent } from './routes/pagina-produto/pagina-produto.component';

import { PaginaPrincipalComponent } from './routes/pagina-principal/pagina-principal.component';
import { PainelHeaderComponent } from './components/painel-header/painel-header.component';
import { PaginaLoginComponent } from './routes/pagina-login/pagina-login.component';
import { UsuarioService } from './services/usuario.service';
import { PaginaSairComponent } from './routes/pagina-sair/pagina-sair.component';
import { PaginaFinalizarComponent } from './routes/pagina-finalizar/pagina-finalizar.component';

@NgModule({
  declarations: [
    AppComponent,
    PaginaProdutoComponent,
    PaginaPrincipalComponent,
    PainelHeaderComponent,
    PaginaLoginComponent,
    PaginaSairComponent,
    PaginaFinalizarComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
  ],
  providers: [
    ServicoProduto, UsuarioService, HttpClient
  ],
  bootstrap: [AppComponent],
  exports: [
    PainelHeaderComponent
  ]
})
export class AppModule { }

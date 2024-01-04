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
import { PaginaOrdensComponent } from './routes/pagina-ordens/pagina-ordens.component';
import { PaginaFinalizadoComponent } from './routes/pagina-finalizado/pagina-finalizado.component';
import { NavProdutosComponent } from './components/nav-produtos/nav-produtos.component';
import { PaginaProdutosComponent } from './routes/pagina-produtos/pagina-produtos.component';
import { PaginaAdminComponent } from './routes/pagina-admin/pagina-admin.component';

@NgModule({
  declarations: [
    AppComponent,
    PaginaProdutoComponent,
    PaginaPrincipalComponent,
    PainelHeaderComponent,
    PaginaLoginComponent,
    PaginaSairComponent,
    PaginaFinalizarComponent,
    PaginaOrdensComponent,
    PaginaFinalizadoComponent,
    NavProdutosComponent,
    PaginaProdutosComponent,
    PaginaAdminComponent
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
    PainelHeaderComponent,
    NavProdutosComponent
  ]
})
export class AppModule { }

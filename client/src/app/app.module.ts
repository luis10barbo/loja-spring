import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ServicoProduto } from './services/servico-produto.service';
import { HttpClient, HttpClientModule, HttpHandler } from '@angular/common/http';
import { PaginaProdutoComponent } from './routes/pagina-produto/pagina-produto.component';

import { RouterModule, Routes } from '@angular/router';
import { PaginaPrincipalComponent } from './routes/pagina-principal/pagina-principal.component';

@NgModule({
  declarations: [
    AppComponent,
    PaginaProdutoComponent,
    PaginaPrincipalComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
  ],
  providers: [
    ServicoProduto, HttpClient
  ],
  bootstrap: [AppComponent],
})
export class AppModule { }

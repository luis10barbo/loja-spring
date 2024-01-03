import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PaginaProdutoComponent } from './routes/pagina-produto/pagina-produto.component';
import { PaginaPrincipalComponent } from './routes/pagina-principal/pagina-principal.component';
import { PaginaLoginComponent } from './routes/pagina-login/pagina-login.component';
import { PaginaSairComponent } from './routes/pagina-sair/pagina-sair.component';
import { PaginaFinalizarComponent } from './routes/pagina-finalizar/pagina-finalizar.component';
import { PaginaOrdensComponent } from './routes/pagina-ordens/pagina-ordens.component';
import { PaginaFinalizadoComponent } from './routes/pagina-finalizado/pagina-finalizado.component';
import { PaginaProdutosComponent } from './routes/pagina-produtos/pagina-produtos.component';

const rotas: Routes = [
  {path: "produto", component: PaginaProdutoComponent},
  {path: "", component:PaginaPrincipalComponent},
  {path: "entrar", component: PaginaLoginComponent},
  {path: "registrar", component: PaginaLoginComponent},
  {path: "sair", component: PaginaSairComponent},
  {path: "finalizar", component: PaginaFinalizarComponent},
  {path: "ordens", component: PaginaOrdensComponent},
  {path: "finalizado", component: PaginaFinalizadoComponent},
  {path: "produtos", component: PaginaProdutosComponent}
]

@NgModule({
  imports: [RouterModule.forRoot(rotas)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

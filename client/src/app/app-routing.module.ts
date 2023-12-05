import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PaginaProdutoComponent } from './routes/pagina-produto/pagina-produto.component';
import { PaginaPrincipalComponent } from './routes/pagina-principal/pagina-principal.component';
import { PaginaLoginComponent } from './routes/pagina-login/pagina-login.component';

const rotas: Routes = [
  {path: "produto", component: PaginaProdutoComponent},
  {path: "", component:PaginaPrincipalComponent},
  {path: "entrar", component: PaginaLoginComponent},
  {path: "registrar", component: PaginaLoginComponent}
]

@NgModule({
  imports: [RouterModule.forRoot(rotas)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

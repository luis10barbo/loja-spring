import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PaginaProdutoComponent } from './routes/pagina-produto/pagina-produto.component';
import { PaginaPrincipalComponent } from './routes/pagina-principal/pagina-principal.component';

const rotas: Routes = [
  {path: "produto", component: PaginaProdutoComponent},
  {path: "", component:PaginaPrincipalComponent}
]

@NgModule({
  imports: [RouterModule.forRoot(rotas)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

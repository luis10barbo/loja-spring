import { ApplicationConfig } from "@angular/platform-browser";
import { provideRouter } from "@angular/router";
import { PaginaProdutoComponent } from "./app/pagina-produto/pagina-produto.component";

export const AppConfig: ApplicationConfig = {
    providers: [
        provideRouter([{path:"/produto", component: PaginaProdutoComponent}])
    ]
}
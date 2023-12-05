import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Usuario } from '../models/usuario';
import { Observable } from 'rxjs';
import { enviroment } from 'src/environment/environment';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  url = enviroment.urlBackend + "/usuarios";

  constructor(private httpClient: HttpClient) { }

  public adquirirEu(): Observable<Usuario | undefined> {
    return this.httpClient.get<Usuario | undefined>(this.url + "/eu", {withCredentials:true});
  }
  
  public entrar(apelido: String, senha: String): Observable<Usuario | undefined> {
    return this.httpClient.post<Usuario | undefined>(this.url + "/entrar", {apelido, senha}, {});
  }

  public registrar(apelido: String, senha: String): Observable<Usuario | undefined> {
    return this.httpClient.post<Usuario | undefined>(this.url + "/registrar", {apelido, senha}, {});
  }
}

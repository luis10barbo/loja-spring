import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Usuario } from '../models/usuario';
import { BehaviorSubject, Observable, Subscription } from 'rxjs';
import { enviroment } from 'src/environment/environment';

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  url = enviroment.urlBackend + "/usuarios";
  usuarioBS: BehaviorSubject<Usuario | undefined> = new BehaviorSubject<Usuario | undefined>(undefined);
  usuario = this.usuarioBS.asObservable();
  usuarioO?: Observable<Usuario | undefined>;

  private subscription?: Subscription;
  constructor(private httpClient: HttpClient) { 
    if (this.subscription) {
      this.subscription.unsubscribe();
    }

    this.atualizarEu();
  }

  public adquirirEu(): Observable<Usuario | undefined> {
    return this.usuario;
  }

  public atualizarEu() {
    this.usuarioO = this.httpClient.get<Usuario | undefined>(this.url + "/eu", {withCredentials:true});
    this.subscription = this.usuarioO.subscribe(data => {
      this.usuarioBS.next(data);
    });
    return this.usuarioO;
  }
  
  public entrar(apelido: String, senha: String): Observable<Usuario | undefined> {
    return this.httpClient.post<Usuario | undefined>(this.url + "/entrar", {apelido, senha}, {withCredentials: true});
  }

  public registrar(apelido: String, senha: String): Observable<Usuario | undefined> {
    return this.httpClient.post<Usuario | undefined>(this.url + "/registrar", {apelido, senha}, {withCredentials: true});
  }

  public sair() {
    return this.httpClient.post(this.url + "/sair", undefined, {withCredentials: true});
  }
}

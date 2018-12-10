import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { Usuario } from './usuario';
import { MessageService } from '../app/message.service';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
};

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  private url = 'http://localhost:8000/RestApp5_Usuarios/usuario';

  constructor(
    private http: HttpClient,
    private messageService: MessageService

  ) { }

  getUsuarios(): Observable<Usuario[]>{
    return this.http.get<Usuario[]>(this.url)
    .pipe(
      tap(usuarios => this.log('fetched usuarios')),
      catchError(this.handleError('getUsuarios', []))
    );    
  }

  getUsuario(id: number): Observable<Usuario>{
    return this.http.get<Usuario>(`${this.url}/${id}`)
    .pipe(
      tap(_ => this.log(`fetched user id=${id}`)),
      catchError(this.handleError<Usuario>(`getUsuario id=${id}`))
    );
  }

  setUsuario(item: Usuario): Observable<Usuario>{
    return this.http.post<Usuario>(this.url, item, httpOptions)
    .pipe(
      tap((usuario: Usuario) => this.log(`added user`)),
      catchError(this.handleError<Usuario>('addUsuario'))
    );
  }

  deleteUsuario(id: number): Observable<Usuario>{
    return this.http.delete<Usuario>(`${this.url}/${id}`)
    .pipe(
      tap(_ => this.log(`deleted user id=${id}`)),
      catchError(this.handleError<Usuario>('deleteUsuario'))
    );
  }

  editUsuario(item: Usuario, id: number): Observable<Usuario>{
    return this.http.put<Usuario>(`${this.url}/${id}`, item, httpOptions)
    .pipe(
      tap(_ => this.log(`updated user id=${id}`)),
      catchError(this.handleError<any>('updateUsuario'))
    );
  }


  private handleError<T> (operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
 
      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead
 
      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`);
 
      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }
 
  /** Log a HeroService message with the MessageService */
  private log(message: string) {
    console.log(message);
    this.messageService.add(`UsuarioService: ${message}`);
  }
}

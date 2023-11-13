import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { LoginViewModel } from '../model/login-view-model';
import { Observable, map } from 'rxjs';
import { User } from '../utils/models/user.interface';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  currentUserEmail: string = '';

  constructor(private httpClient: HttpClient) { };

  public login( loginViewModel : LoginViewModel ): Observable<User> {
    return this.httpClient.post<User>(`http://localhost/angular/ngEasyPortfolio/src/app/services/api/token/login.php?action=login`, JSON.stringify(loginViewModel), {responseType: "json"})
    .pipe(map(user => {
      if(user){
        this.currentUserEmail = user.email ;
        sessionStorage.setItem('currentUser', JSON.stringify(user)); 
        
      }
      return user ;
    }))
  }

  public logout(){
    this.currentUserEmail = '';
    sessionStorage.removeItem('currentUser');
  }




}

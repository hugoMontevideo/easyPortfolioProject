import { HttpBackend, HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { environment } from 'src/environments/environment';
import { LoginView } from '../login/login-email-pwd.interface';
import { LoginUser } from '../login/login-user.interface';
import { JWTTokenService } from './JWTToken.service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  ENV_BASE :string = environment.baseUrl;
  ENV_DEV : string = environment.apiUrl;
  
  loginUser: LoginUser ={
                          id:0,
                          email:"",
                          token: ""
                        } ;
  
  httpClient!: HttpClient; // de cette façon on évite l'interceptor (middleware)

  constructor(
  
      private route : Router,
      private httpBackend: HttpBackend
      ) { };

  public login( loginView : LoginView ): Observable<any> {
    // on fait une instance de httpClient et on empeche l'ajout de middleware
    this.httpClient = new HttpClient(this.httpBackend);

    return this.httpClient.post<any>(`${this.ENV_BASE}/auth/authorize`, loginView, {responseType: "json"})
    .pipe(map(data => {
      if(data){
        this.loginUser.id = data.user.id,
        this.loginUser.email = data.user.login ;
        this.loginUser.token = data.token;
        sessionStorage.setItem('currentUser', JSON.stringify(this.loginUser)); 
        console.log(data);
      }
      return data ;
    }))
  }

  public logout(){
    this.loginUser = {
      id:0,
      email:"",
      token: ""
    } ;
    sessionStorage.removeItem('currentUser');
  }




}

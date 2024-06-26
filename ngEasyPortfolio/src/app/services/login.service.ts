import { HttpBackend, HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { environment } from 'src/environments/environment';
import { LoginEmailPwd } from '../login/login-email-pwd.interface';
import { JWTTokenService } from './JWTToken.service';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  ENV_BASE :string = environment.baseUrl;
  ENV_DEV : string = environment.apiUrl;
  
  httpClient!: HttpClient; // de cette façon on évite l'interceptor (middleware)

  constructor(
        private router : Router,
        private httpBackend: HttpBackend,
        private jwtToken: JWTTokenService
      ) { };

  public login( loginEmailPwd : LoginEmailPwd ): Observable<any> {
    // on fait une instance de httpClient et on empeche l'ajout de middleware
    this.httpClient = new HttpClient(this.httpBackend);

    return this.httpClient.post<any>(`${this.ENV_BASE}/auth/authorize`, loginEmailPwd )
      .pipe(map(data => {
        if(data){
          this.jwtToken.setToken(data.token);        
        }
        return data ;
      }))
  }

  public logout = ():void => {
    this.jwtToken.removeToken();
  }

  // verify if user is logged then logout
  onLogin = ():void => {
    if( this.jwtToken.isLogged() ){
      this.logout();
      this.router.navigateByUrl("/");
    }
  }

}

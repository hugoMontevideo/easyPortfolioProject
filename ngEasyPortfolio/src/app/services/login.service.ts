import { HttpBackend, HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { environment } from 'src/environments/environment';
import { LoginEmailPwd } from '../login/login-email-pwd.interface';
import { LoginUser } from '../login/login-user.interface';
import { JWTTokenService } from './JWTToken.service';
import { Router } from '@angular/router';
import { SessionStorageService } from './session-storage.service';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  ENV_BASE :string = environment.baseUrl;
  ENV_DEV : string = environment.apiUrl;
  
  loginUser: LoginUser ={
                          email:"",
                          token: ""
                        } ;
  
  httpClient!: HttpClient; // de cette façon on évite l'interceptor (middleware)

  constructor(
        private route : Router,
        private storageService : SessionStorageService,
        private httpBackend: HttpBackend,
        private jwtToken: JWTTokenService
      ) { };

  public login( loginEmailPwd : LoginEmailPwd ): Observable<any> {
    // on fait une instance de httpClient et on empeche l'ajout de middleware
    this.httpClient = new HttpClient(this.httpBackend);

    return this.httpClient.post<any>(`${this.ENV_BASE}/auth/authorize`, loginEmailPwd, {responseType: "json"})
    .pipe(map(data => {
      if(data){
        this.jwtToken.setToken(data.token);        
        this.loginUser.token = data.token;
        this.storageService.setToken(data.token);   
      }
      return data ;
    }))
  }

  public logout(){
    this.loginUser = {
      email:"",
      token: ""
    } ;
    sessionStorage.removeItem("token");
  }

  




}

import { HttpBackend, HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map, catchError, throwError } from 'rxjs';
import { environment } from 'src/environments/environment';
import { LoginEmailPwd } from '../login/login-email-pwd.interface';
import { JWTTokenService } from './JWTToken.service';
import { Router } from '@angular/router';
import { LoginResetDto } from '../login-reset/login-reset-dto.interface';
import { LoginEmailPwdCode } from '../login-reset/login-email-pwd-code.interface';
import { LoginVerifyCodeDto } from '../login-reset/login-verify-code-dto.interface';
import { AnyCatcher } from 'rxjs/internal/AnyCatcher';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  ENV_BASE :string = environment.baseUrl;
  // ENV_DEV : string = environment.apiUrl;
  
  httpClient!: HttpClient; // de cette façon on évite l'interceptor (middleware)

  errorMsg!: String;

  constructor(
        private router : Router,
        private httpBackend: HttpBackend,
        private jwtToken: JWTTokenService
      ) { };


  login = ( loginEmailPwd : LoginEmailPwd ): Observable<any> => {
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


  resetPasswordRequest = ( email : string ):Observable<LoginResetDto> => {
     // on fait une instance de httpClient et on empeche l'ajout de middleware
     this.httpClient = new HttpClient(this.httpBackend);

    return this.httpClient.get<LoginResetDto>(`${this.ENV_BASE}/auth/reset-password-request/${email}`)
    .pipe(catchError(this.handleError)); // catch validator error

  }

  verifyCode = ( loginEmailPwdCode : LoginEmailPwdCode ):Observable<LoginVerifyCodeDto> => {
    // on fait une instance de httpClient et on empeche l'ajout de middleware
    this.httpClient = new HttpClient(this.httpBackend);

   return this.httpClient.post<LoginVerifyCodeDto>(`${this.ENV_BASE}/auth/verify-code`, loginEmailPwdCode )
   .pipe(catchError(this.handleError)); // catch validator error

 }


  resetPassword = ( loginEmailPwdCode : LoginEmailPwdCode ):Observable<any> => {
    // on fait une instance de httpClient et on empeche l'ajout de middleware
    this.httpClient = new HttpClient(this.httpBackend);

   return this.httpClient.put<any>(`${this.ENV_BASE}/auth/reset-password`, loginEmailPwdCode)
   .pipe(catchError(this.handleError)); // catch validator error

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

  private handleError = (error: HttpErrorResponse):Observable<never> => {
    
    this.errorMsg= error.error.message;
    return throwError(()=>this.errorMsg);
  }


}

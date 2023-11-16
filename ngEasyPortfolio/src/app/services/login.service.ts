import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, map } from 'rxjs';
import { User } from '../user/user.interface';
import { environment } from 'src/environments/environment';
import { LoginView } from '../login/login-view.interface';
import { LoginUser } from '../login/login-user.interface';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  ENV_BASE :string = environment.baseUrl;
  loginUser: LoginUser ={
    id:0,
    email:"",
    token: ""
  } ;

  constructor(private httpClient: HttpClient) { };

  public login( loginView : LoginView ): Observable<any> {
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

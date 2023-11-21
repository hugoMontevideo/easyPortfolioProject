import { Component } from '@angular/core';
import { LoginService } from '../services/login.service';
import { Router } from '@angular/router';
import { LoginView } from './login-email-pwd.interface';
import { LoginUser } from './login-user.interface';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  loginView: LoginView = {
    username: "",
    password: ""
  } ;
  loginUser: LoginUser = {
    id: 0,
    email: '',
    token: ''
  } ;
  loginError: string = "";

  constructor( 
          private loginService: LoginService,
          private router : Router
      ){ }

  onLoginClick(event:any){
    this.loginService.login(this.loginView).subscribe({
      next:(user) => { 
        console.log(user);
                            this.router.navigateByUrl("/portfolios");
                        },    
      error: (err:Error) => {
                              this.loginError="Mdp ou email invalides";
                        },
      complete: ()=> {console.log("ok vers portfolio");
      }
    })
  }




}

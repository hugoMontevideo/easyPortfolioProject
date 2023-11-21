import { Component, OnInit } from '@angular/core';
import { LoginService } from '../services/login.service';
import { Router } from '@angular/router';
import { LoginEmailPwd } from './login-email-pwd.interface';
import { LoginUser } from './login-user.interface';
import { SessionStorageService } from '../services/session-storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginEmailPwd: LoginEmailPwd = {
                  email: "", 
                  password: ""
                } ;
  loginUser: LoginUser = {
              login: '',
              token: '',
              conButton: "Connexion"
            } 
  loginError: string = "";

  constructor( 
          private loginService: LoginService,
          private router : Router,
          private storageService: SessionStorageService
      ){ }

  ngOnInit(): void {
    this.loginUser.login = this.storageService.getLogin();
    
    if( this.loginUser.login != null){   
      this.storageService.clearStorage();
      this.router.navigateByUrl("/");
    }
  }

  onLoginClick(event:any){
    this.loginService.login(this.loginEmailPwd)
    .subscribe({
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

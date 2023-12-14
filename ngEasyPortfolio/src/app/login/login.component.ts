import { Component, OnInit } from '@angular/core';
import { LoginService } from '../services/login.service';
import { Router } from '@angular/router';
import { LoginEmailPwd } from './login-email-pwd.interface';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  ENV_ICONS: string = `${environment.apiIcons}/`;
  loginEmailPwd: LoginEmailPwd = {
                  email: "", 
                  password: ""
                } ;
  loginError: string = "";

  constructor( 
          private loginService: LoginService,
          private router : Router,
      ){ }

  ngOnInit(): void {  
    // if user is logged => logout  
    this.loginService.onLogin();
  }

  onLoginClick(event:any){
    this.loginService.login(this.loginEmailPwd)
    .subscribe({
      next:(user) => {                                      
                this.router.navigateByUrl("/portfolios");
      },    
      error: (err:Error) => {
                this.loginError="Mot de passe ou email invalides";
      },
      complete: ()=> {console.log("ok vers portfolio");
      }
    })
  }




}

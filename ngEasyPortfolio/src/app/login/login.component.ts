import { Component } from '@angular/core';
import { LoginViewModel } from './../model/login-view-model';
import { LoginService } from '../services/login.service';
import { Router } from '@angular/router';
import { User } from '../utils/models/user.interface';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  loginViewModel: LoginViewModel = new LoginViewModel();
  loginError: string = "";

  constructor( 
          private loginService: LoginService,
          private router : Router
      ){ }

  onLoginClick(event:any){
    this.loginService.login(this.loginViewModel).subscribe({
      next:(user:User) => { console.log(user);
                            this.router.navigateByUrl("/portfolio");
                        },    
      error: (err:Error) => {
                              console.log("Error");
                              this.loginError="Mdp ou email invalides";
                        },
      complete: ()=> {console.log("ok vers portfolio");
      }
    })
  }




}

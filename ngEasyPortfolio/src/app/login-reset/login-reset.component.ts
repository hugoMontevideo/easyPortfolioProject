import { Component } from '@angular/core';
import { LoginService } from '../services/login.service';
import { Router } from '@angular/router';
import { environment } from 'src/environments/environment';
import { LoginResetDto } from './login-reset-dto.interface';
import { LoginEmailPwdCode } from './login-email-pwd-code.interface';
import { LoginVerifyCodeDto } from './login-verify-code-dto.interface';
import { LoginEmailPwd } from '../login/login-email-pwd.interface';

@Component({
  selector: 'app-login-reset',
  templateUrl: './login-reset.component.html',
  styleUrl: './login-reset.component.scss'
})
export class LoginResetComponent {
  ENV_ICONS: string = `${environment.apiIcons}/`;
  loginEmailPwd!:LoginEmailPwd;
  loginEmailPwdCode: LoginEmailPwdCode = {
                  code:0,
                  email: "", 
                  password: ""
                } ;
  loginError: string="";
  loginInfo: string="";
  userFound: boolean = false;
  verifyCode: boolean = false;

  passwordVerify: string = "";

  constructor( 
          private loginService: LoginService,
          private router : Router,
      ){ }

  ngOnInit(): void {  
    // if user is logged => logout  
    //this.loginService.onLogin();
  }

  onSubmitReset = () => { // reset password request ****** 
    console.log("email : ",this.loginEmailPwdCode.email);
    this.loginService.resetPasswordRequest(this.loginEmailPwdCode.email)
    .subscribe({
      next:(data:LoginResetDto) => {      
        console.log(data);
        
        this.loginInfo=`Le code a été envoyé à : ${data.email}`;
        this.loginError=``;
        this.userFound=true;
      },    
      error: (err:Error) => {
                console.log(err)
                
                let message: string|any = err;
                this.loginError=message;      
      }
    })
  }

  onSubmitCode = () => { // reset password request ****** 
    this.loginService.verifyCode(this.loginEmailPwdCode)
    .subscribe({
      next:(data:LoginVerifyCodeDto) => {
        console.log(data);
        if(data.code==true && data.expires==false){
          this.loginInfo=``;
          this.loginError=``;
          this.userFound=true;
          this.verifyCode=true;
        }else if (data.expires==true) {
          
          this.loginInfo=``;
          this.loginError="Le code a expiré. Veuillez faire une nouvelle demande de modification de mot de passe";
          this.userFound=false;
          this.verifyCode=false;
        } else if(data.code==false) {
          this.loginInfo=``;
          this.loginError=`Le code n'est pas correct. Veuillez vérifier le code reçu par mail.`;
          this.userFound=true;
          this.verifyCode=false;
        }
      },    
      error: (err:Error) => {
                console.log(err) 
                let message: string|any = err;
                this.loginError=message;
                
      }
    })
  }

  onSubmitNewPassword = (): void => {
    if( this.loginEmailPwdCode.password == this.passwordVerify ){
      console.log(this.loginEmailPwdCode);
      this.loginService.resetPassword(this.loginEmailPwdCode)
      .subscribe({
        next:(data) => {   
          this.loginEmailPwd = {
            email: this.loginEmailPwdCode.email, 
            password: this.loginEmailPwdCode.password
          } ; 
          this.loginError=``;       
          this.loginInfo=`Votre mot de passe a bien été modifié.`;
          setTimeout(() => {
            this.loginService.login(this.loginEmailPwd)
            .subscribe({
              next:(user) => {                                      
                        this.router.navigateByUrl("/portfolios");
              },    
              error: (err:Error) => {
                        this.loginError="Mot de passe ou email invalides";
              }
            })
          }, 3000); 
        },    
        error: (err:Error) => {
                  let message: string|any = err;
                  this.loginError=message;
                  
        }
      })

    }else{
      this.loginError=`Le mot de passe et la vérification doivent être identiques.`;
    }


    

  }



}

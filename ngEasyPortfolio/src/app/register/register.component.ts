import { Component } from '@angular/core';

import { Router } from '@angular/router';
import { Register } from './register.interface';
import { RegisterService } from '../services/register.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  ENV_ICONS: string = `${environment.apiIcons}/`;
  register: Register = {
    email: "", 
    password: ""
  } ;
  registerError: string = "";

  constructor( 
  private registerService: RegisterService,
  private router : Router,
  ){ }

  // ngOnInit(): void {  
  // // if user is logged => logout  
  // this.loginService.onLogin();
  // }

  onConnectionClick(event:any){
  this.registerService.registerUser(this.register)
    .subscribe({
      next:(user) => {                                      
        this.router.navigateByUrl("/portfolios");
      },    
      error: (err:Error) => {
        this.registerError="Mot de passe ou email invalides";
      },
      complete: ()=> {console.log("ok vers portfolio");
      }
    });
  }




}


















//   table: string = "users";
//   user: User = {
//               id: 0,
//               name: "",
//               firstname: "",
//               email: "",
//               password: ""
//             };

//   constructor(private http: HttpService){}

//   onSubmit= (form : NgForm)=>{
//     this.http.add(this.table, form.value ).subscribe({
//       next: (response)=>console.log(response),
//       error: (err: Error)=> console.log("add user error ", err),
//       complete: ()=>console.log("insert user ok")
//     })
//   }

// }

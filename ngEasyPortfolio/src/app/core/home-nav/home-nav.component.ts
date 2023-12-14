import { Component } from '@angular/core';
import { JWTTokenService } from 'src/app/services/JWTToken.service';
import { User } from '../user/user.interface';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-home-nav',
  templateUrl: './home-nav.component.html',
  styleUrls: ['./home-nav.component.scss']
})

export class HomeNavComponent {

  currentUser: User = {
                        id: -1,
                        name: "",
                        firstname: "",
                        email: "",
                        password: "",
                        dateInscription: new Date("1970-01-01"),
                        dateConnect: new Date("1970-01-01"), 
                        profileImgPath:"",
                        portfolios: [],
                        roles: []
                        // role!: string;
                        // token!: string;
                        // confirmed!: number;
                      }

  constructor(
    public jwtToken: JWTTokenService,
    private userService: UserService
  ){}

  getUserByEmail =():void => {
    this.currentUser.email = this.jwtToken.getUser();
    this.userService.getUserByEmail(this.currentUser.email)
      .subscribe({
        next:(data:User)=> this.currentUser = data,
        error: (err:Error)=>console.log("Error userbyId")
    })
  }


  
}

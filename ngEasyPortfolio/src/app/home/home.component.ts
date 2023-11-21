import { Component, OnInit } from '@angular/core';
import { LoginUser } from '../login/login-user.interface';
import { SessionStorageService } from '../services/session-storage.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {
  loginUser: LoginUser ={
    login:"",
    token: "",
    conButton:""
  } 
  constructor( 
    private storageService: SessionStorageService
  )
  {};

  ngOnInit(): void {
    this.loginUser.login = this.storageService.getLogin();
    this.loginUser.conButton = this.storageService.getConButton();    
    
    // // je dois passer par une variable intermediaire pour pouvoir recup currentUser
    if( this.loginUser.conButton == null){
      this.loginUser.conButton = "Connexion"
    }
  }
}

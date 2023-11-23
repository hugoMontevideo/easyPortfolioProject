import { Component, Input, OnInit } from '@angular/core';
import { JWTTokenService } from 'src/app/services/JWTToken.service';
import { LoginService } from 'src/app/services/login.service';
import { SessionStorageService } from 'src/app/services/session-storage.service';
// import { FontAwesomeModule, FaIconLibrary } from '@fortawesome/angular-fontawesome';


@Component({
  selector: 'app-home-nav',
  templateUrl: './home-nav.component.html',
  styleUrls: ['./home-nav.component.scss']
})
export class HomeNavComponent implements OnInit {
  
  userEmail!: string;

  constructor(
    public jwtToken: JWTTokenService,
  ){}

  ngOnInit(): void {
  
  }


  
}

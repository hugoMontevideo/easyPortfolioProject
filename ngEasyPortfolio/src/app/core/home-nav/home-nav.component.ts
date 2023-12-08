import { Component, Input, OnInit } from '@angular/core';
import { JWTTokenService } from 'src/app/services/JWTToken.service';

@Component({
  selector: 'app-home-nav',
  templateUrl: './home-nav.component.html',
  styleUrls: ['./home-nav.component.scss']
})

export class HomeNavComponent {

  constructor(
    public jwtToken: JWTTokenService,
  ){}

 


  
}

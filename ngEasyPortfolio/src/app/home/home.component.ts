import { Component, OnInit } from '@angular/core';
import { PortfolioService } from '../portfolio/services/portfolio.service';
import { User } from '../core/user/user.interface';
import { JWTTokenService } from '../services/JWTToken.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.scss']
})
export class HomeComponent implements OnInit {

  user: User = {
    id: -1,
    name: "",
    firstname: "",
    email: "",
    password: "",
    dateInscription: new Date("1970-01-01"),
    dateConnect: new Date("1970-01-01"),
    profileImgPath: "",
    roles: []
  }

  constructor( 
    private jwtService: JWTTokenService,
    private portfolioService: PortfolioService
  ){};

  ngOnInit(): void {
    this.jwtService.jwtToken = this.jwtService.getToken();
   
  }
}

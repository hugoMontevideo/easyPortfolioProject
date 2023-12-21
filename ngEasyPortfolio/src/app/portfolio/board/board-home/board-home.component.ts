import { Component, Input, SimpleChanges } from '@angular/core';
import { Portfolio } from '../../model/portfolio/portfolio.interface';

import { PortfolioService } from '../../services/portfolio.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-board-home',
  templateUrl: './board-home.component.html',
  styleUrls: ['./board-home.component.scss']
})
export class BoardHomeComponent {

  ENV_ICONS: string = `${environment.apiIcons}/`;
  @Input() portfolio:Portfolio = {
                                  id: -1,
                                  title: "",
                                  description: "",
                                  name: "",
                                  firstname: "",
                                  email:"",
                                  city: "",
                                  profileImgPath: "",
                                  aboutMe: "",
                                  projects: [],
                                  educations:[],
                                  experiences:[],
                                  skills: [],
                                  user: {
                                    id: 0,
                                    name: "",
                                    firstname: "",
                                    email: "",
                                    password: "",
                                    dateInscription: new Date("1970-01-01"),
                                    dateConnect: new Date("1970-01-01"),
                                    profileImgPath: "",
                                    roles: []
                                  }
                                };
  @Input() portfolioId:number = -1;

  constructor( private portfolioService: PortfolioService ){};

  ngOnChanges(changes: SimpleChanges){  

    this.portfolioService.getPortfolioById(this.portfolioId) // refresh projects []
          .subscribe({
              next: (data:Portfolio) => { this.portfolio = data 
                console.log(data);
                
              },
              error: (err:Error)=>{console.error("**error Getting Projects**");} //TODO *******
            });    
  }


}

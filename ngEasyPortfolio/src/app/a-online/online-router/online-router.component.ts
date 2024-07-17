import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Portfolio } from 'src/app/portfolio/model/portfolio/portfolio.interface';
import { PortfolioService } from 'src/app/portfolio/services/portfolio.service';

@Component({
  selector: 'app-online-router',
  templateUrl: './online-router.component.html',
  styleUrl: './online-router.component.scss'
})
export class OnlineRouterComponent implements OnInit {

  portfolio: Portfolio = {
      id: -1,
      title: "",
      description: "",
      name: "",
      firstname: "",
      email:"",
      profileImgPath: "",
      aboutMe: "",
      city: "",
      projects: [],
      educations:[],
      experiences:[],
      skills: [],
      socials: [],
      categoryPortfolioId: -1,
  }

  constructor(
    private route: ActivatedRoute,
    private portfolioService: PortfolioService,
  ){};

  ngOnInit(): void {
    this.portfolio.id = this.portfolioService.getId(this.route.snapshot.paramMap.get('id'));
    this.portfolioService.getPortfolioByIdOnline(this.portfolio.id)
      .subscribe({
        next:(response:Portfolio) => { 
                this.portfolio = response;
                console.log(this.portfolio.categoryPortfolioId);
                
              }, 
        error: (err:Error) => {
                  // TODO  manage error response
                  console.error("Error portfolioById")
              }
      });
    
  }


  

 

}

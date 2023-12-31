import { Component, OnInit} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Portfolio } from 'src/app/portfolio/model/portfolio/portfolio.interface';
import { PortfolioService } from 'src/app/portfolio/services/portfolio.service';
import { environment } from 'src/environments/environment';
import { ViewportScroller } from '@angular/common';

@Component({
  selector: 'app-template-dev',
  templateUrl: './template-dev.component.html',
  styleUrls: ['./template-dev.component.scss']
})

export class TemplateDevComponent implements OnInit {
  ENV_ICONS: string = `${environment.apiIcons}/`;
  ENV_PICT:string = `${environment.apiImg}/pictures/`;
  burger = false;

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
    }
    
    constructor(
      private route: ActivatedRoute,
      private portfolioService: PortfolioService,
      private viewportScroller : ViewportScroller
    ){};
  
  ngOnInit(): void {
    this.portfolio.id = this.portfolioService.getId(this.route.snapshot.paramMap.get('id'));

    this.portfolioService.getPortfolioById(this.portfolio.id)
      .subscribe({
        next:(response:Portfolio) => { 
                                    this.portfolio = response;                                                                    
                                  }, 
        error: (err:Error) => {
                        // TODO  manage error response
                        console.error("Error portfolioById")
                    }
    });
  }

  onBurger = ()=>{
    this.burger=!this.burger;    
  }

  scrollToSection = (sectionId : string):void =>{   
    this.viewportScroller.scrollToAnchor(sectionId);
    this.burger=false;
  }






}

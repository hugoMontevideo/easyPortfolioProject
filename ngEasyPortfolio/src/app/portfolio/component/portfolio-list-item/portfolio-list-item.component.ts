import { Component, OnInit } from '@angular/core';
import { Portfolio} from '../../model/portfolio/portfolio.interface';
import { ActivatedRoute } from '@angular/router';
import { PortfolioService } from '../../services/portfolio.service';


@Component({
  selector: 'app-portfolio-list-item',
  templateUrl: './portfolio-list-item.component.html',
  styleUrls: ['./portfolio-list-item.component.scss']
})
export class PortfolioListItemComponent implements OnInit {

  table: string = 'portfolios';
  portfolioId:number = -1;
  legend!: string; // form legend
  portfolio: Portfolio = {
        id: -1,
        title: "",
        description: "",
        name: "",
        firstname: "",
        email:"",
        city: "",
        projects: [],
        educations:[],
        experiences:[],
        skills: []
        // u_id!: number;
    }
    
  constructor(
      private route: ActivatedRoute,
      private portfolioService: PortfolioService,
    ){};

  ngOnInit(): void {
      this.portfolioId =  this.portfolioService.getId(this.route.snapshot.paramMap.get('id'));

      this.portfolioService.getPortfolioById(this.table, this.portfolioId)
      .subscribe({
        next:(response:Portfolio) => { this.portfolio = response }, 
        error: (err:Error) => console.log("Error portfolioById")
      });
      
  }

  

}

import { Component, OnInit } from '@angular/core';
import { Portfolio } from '../../model/portfolio/portfolio.interface';
import { PortfolioService } from '../../services/portfolio.service';
import { JWTTokenService } from 'src/app/services/JWTToken.service';
import { User } from 'src/app/core/user/user.interface';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-portfolio-list',
  templateUrl: './portfolio-list.component.html',
  styleUrls: ['./portfolio-list.component.scss']
})
export class PortfolioListComponent implements OnInit {
  portfolios: Portfolio[]=[];
  legend: string = "";
  inputError?: string;
  isPortfolioFormShowing: boolean = false; // display or hide form
  allDisplay=false;
  isProtected = true;

  newPortfolio: Portfolio = {
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
    socials: [],
    user: {
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
  };

  constructor( private portfolioService: PortfolioService,
                private jwtService: JWTTokenService,
              ){};

  ngOnInit(): void {
    this.jwtService.jwtToken = this.jwtService.getToken();
    this.getAllPortfolios(); 

  }

  public onCloseModalForm = () => {
    this.isPortfolioFormShowing = false;
    this.newPortfolio = this.portfolioService.resetNewPortfolio(this.newPortfolio.user);
  }
  public onAddPortfolio = () => {
    this.legend = "Ajouter un portfolio"
    this.isPortfolioFormShowing = true; // display form
    this.portfolioService.add(this.newPortfolio)
      .subscribe({
         next:(data)=>{
            this.newPortfolio = data; 
            // refresh portfolios          
            this.portfolioService.getAllPortfolios(this.jwtService.getUser()) // refresh projects[]
            .subscribe({
                next: (data:Portfolio[]) => { 
                        this.portfolios = data
                       },
                error: (err:Error)=>{console.error("**error Getting Projects**");} //TODO *******
              });
         },
        error:(_error)=>{
          console.log("**error Adding Portfolio**");
          if(_error instanceof HttpErrorResponse ) {
            this.inputError = _error.error.title;
          } 
        }
      });
  }

  getAllPortfolios= () =>{
    this.portfolioService.getUserByEmail()
    .subscribe({
      next:(data:User)=>{ 
            this.newPortfolio.user = data
            this.portfolioService.getAllPortfolios(this.newPortfolio.user.email)
              .subscribe({
                next: (response:Portfolio[] ) => {
                                this.portfolios=response;                      
                              },
                error: (_err: Error)=> { alert("Error getting portfolios")} 
              });
      },
      error: (err:Error)=>console.log("Error userbyEmail")
    })
  }

  public onEditPortfolio = (index: number) => {
    this.legend = "Modifier le portfolio"
    this.isPortfolioFormShowing = true;
    this.newPortfolio = this.portfolios[index];     
  }

  onDeletePortfolio = (portfolioId : number ):void => {
     this.portfolioService.deletePortfolio( portfolioId )
       .subscribe({
        next:( )=> {
            this.portfolioService.getAllPortfolios(this.jwtService.getUser()) // refresh projects[]
               .subscribe({
                    next: (data:Portfolio[]) => { 
                            this.portfolios = data;
                          },
                    error: (_error: Error)=>{
                        console.log("**error Getting Projects**"); 
                      }
                });
        },
       error:(err:Error)=>{ console.log("Error while deleting portfolio.");
          }
       }) 
  };

  public onSubmitPortfolio = ()=>{    
    this.portfolioService.savePortfolio(this.newPortfolio)
      .subscribe({
          next:(data)=>{
              this.isPortfolioFormShowing = false;  // hide the form
              this.newPortfolio = this.portfolioService.resetNewPortfolio(this.newPortfolio.user);
              this.portfolioService.getAllPortfolios(this.jwtService.getUser()) // refresh portfolios[]
                .subscribe({
                    next: (data:Portfolio[]) => { 
                            this.portfolios = data;
                          },
                    error: (_error: Error)=>{
                        console.log("**error Getting Portfolios**"); 
                      }
                  });
            },
          error:(_error)=>{
            console.error("**error updating Project**");
            if(_error instanceof HttpErrorResponse ) {
              this.inputError = _error.error.title;
            } 
          }
      });
  
  }
  
}


 

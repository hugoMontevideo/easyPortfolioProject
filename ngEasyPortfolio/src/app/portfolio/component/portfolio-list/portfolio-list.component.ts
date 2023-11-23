import { Component, OnInit } from '@angular/core';
import { Portfolio } from '../../model/portfolio/portfolio.interface';
import { PortfolioService } from '../../services/portfolio.service';
import { LoginUser } from 'src/app/login/login-user.interface';
import { SessionStorageService } from 'src/app/services/session-storage.service';
import { JWTTokenService } from 'src/app/services/JWTToken.service';

@Component({
  selector: 'app-portfolio-list',
  templateUrl: './portfolio-list.component.html',
  styleUrls: ['./portfolio-list.component.scss']
})
export class PortfolioListComponent implements OnInit {

  table: string = "portfolios";
  portfolios!: Portfolio[];
  loginUser: LoginUser ={
                email:"",
                token: ""                
              } 

  constructor( private portfolioService: PortfolioService,
                private jwtTokenService: JWTTokenService
              )
              {};

  ngOnInit(): void {
    
    if( this.jwtTokenService.getUser() != null){  
      this.loginUser.email = this.jwtTokenService.getUser(); 
      this.portfolioService.getAllPortfolios(this.table)
      .subscribe({
        next: (response: Portfolio[]) => {this.portfolios=response},
        error: (err: Error)=> { alert("Error getting portfolios")} 
      });
      
    }
  }

    onUpdateClick(){
      // this.projectService.updateProject(this.editProject, this.table).subscribe({
      //   next: (response: Project) => {
      //                     if(response.name != 'Erreur'){
      //                       this.projects[this.editIndex]=response;
      //                       this.getProjects;
      //                     }else{console.log('erreur la modif n\'a pas été realisée');}
      //                 },
      //   error: (err: Error )=> {console.log('Error '+ err)},
      //   complete: ()=> { 
      //               this.editProject.id=0;
      //               this.editProject.dateStart='';
      //               this.editProject.name='';
      //               this.editProject.teamSize=0;
      //           }
      // });
    }

 
  

}

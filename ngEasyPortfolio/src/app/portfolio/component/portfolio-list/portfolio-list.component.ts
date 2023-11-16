import { Component } from '@angular/core';
import { Portfolio } from '../../model/portfolio/portfolio.interface';
import { PortfolioService } from '../../services/portfolio.service';
import { ActivatedRoute } from '@angular/router';
import { User } from 'src/app/user/user.interface';
import { LoginUser } from 'src/app/login/login-user.interface';

@Component({
  selector: 'app-portfolio-list',
  templateUrl: './portfolio-list.component.html',
  styleUrls: ['./portfolio-list.component.scss']
})
export class PortfolioListComponent {

  table: string = "portfolios";
  portfolios!: Portfolio[];
  currentUser: User =  {
                id: 0,
                name: "",
                firstname: "",
                email: "",
                password: ""
              };
  loginUser: LoginUser ={
                id:0,
                email:"",
                token: ""
              } ;

  constructor( private portfolioService: PortfolioService,
              private route: ActivatedRoute)// *** a enlever + tard ***
              {};

  ngOnInit(): void {
    let storage: any = sessionStorage.getItem("currentUser");
    
    // // je dois passer par une variable intermediaire pour pouvoir recup currentUser
    if( storage != null){
      // this.loginUser = JSON.parse(storage);
      this.getPortfolios(this.table);
    }

  }

  public getPortfolios = (table: string) => {    
    this.portfolioService.getAll(table)
    .subscribe({
      next: (response: Portfolio[]) => { 
        this.portfolios=response 
      },
      error: (err: Error)=> {
              alert("Error getting portfolios")
            },
      complete: ()=> console.log(this.portfolios)
    })
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

import { Component } from '@angular/core';
import { Portfolio } from '../../model/portfolio/portfolio.interface';
import { PortfolioService } from '../../services/portfolio.service';
import { ActivatedRoute } from '@angular/router';
import { User } from 'src/app/utils/models/user.interface';

@Component({
  selector: 'app-portfolio-list',
  templateUrl: './portfolio-list.component.html',
  styleUrls: ['./portfolio-list.component.scss']
})
export class PortfolioListComponent {

  table: string = "portfolioS";
  portfolios!: Portfolio[];
  currentUser: User =  {
                id: 0,
                name: "",
                firstname: "",
                email: "",
                password: ""
              };

  constructor( private portfolioService: PortfolioService,
              private route: ActivatedRoute)// *** a enlever + tard ***
              {}

  ngOnInit(): void {
    // let anything: any = sessionStorage.getItem("currentUser");
    // // je dois passer par une variable intermediaire pour pouvoir recup currentUser
    // if( anything != null){
    //   this.currentUser = JSON.parse(anything);
    //   this.getPortfolios(this.table, this.currentUser.id);
    // }

  }

  getPortfolios(table: string, userId:number ){
    this.portfolioService.getAll(table, userId).subscribe({
      next: (response: Portfolio[]) => { this.portfolios=response },
      error: (err: Error)=> {
              alert("Authentication failed, error getting portfolios")
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

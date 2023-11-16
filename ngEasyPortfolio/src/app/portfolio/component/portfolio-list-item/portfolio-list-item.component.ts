import { Component, Input, OnInit } from '@angular/core';
import { Portfolio} from '../../model/portfolio/portfolio.interface';
import { ActivatedRoute } from '@angular/router';

import { Observable } from 'rxjs';
import { Skill } from '../../model/skill/skill.interface';
import { User } from 'src/app/user/user.interface';
import { PortfolioService } from '../../services/portfolio.service';

@Component({
  selector: 'app-portfolio-list-item',
  templateUrl: './portfolio-list-item.component.html',
  styleUrls: ['./portfolio-list-item.component.scss']
})
export class PortfolioListItemComponent implements OnInit {
  table: string = 'portfolios';
  id: number|any;
  currentUser!: User;
  portfolio!: Portfolio;
  skills!: Skill[];


  constructor(
      private route: ActivatedRoute,
      private portfolioService: PortfolioService
    ){};

  
  ngOnInit(): void {
    let anything: any = sessionStorage.getItem("currentUser");
    // je dois passer par une variable intermediaire pour pouvoir recup currentUser
    // if( anything != null){
    //   this.currentUser = JSON.parse(anything);
    //   this.id = this.route.snapshot.paramMap.get('id');
  
    //   if(this.id != null){
    //     this.http.getById(this.table, this.currentUser.id, this.id)
    //     .subscribe({
    //       next:(response:Portfolio)=>{ this.portfolio = response},
    //       error:(err:Error)=>{console.log(err);
    //       },
    //       complete:()=>{}
    //     })
    //   }
    // }
    this.getPortfolioById(this.table, 1);
  }

  getPortfolioById(table:string, id:number){
    this.portfolioService.getPortfolioById(table, id)
    .subscribe({
      next:(response:Portfolio)=> this.portfolio = response,
      error: (err:Error)=>console.log("Error portfolioById"),
      complete: ()=> console.log(this.portfolio.title)
    })
  }

  onEditClick(event: any) {
    // console.log (this.projects[index].dateStart.slice(0,10));  **  formater date pour affichage html
    // console.log(index);
    
    // this.currentUser.id= this.users[index].id;
    // this.editProject.name= this.projects[index].name;
    // this.editProject.dateStart= this.projects[index].dateStart.slice(0,10);
    // this.editProject.teamSize= this.projects[index].teamSize;

    // this.getUsers(this.table, this.currentUser.id);

 
    // this.currentIndex=index;
  }

  onDeleteClick(event:any){
    // this.deleteIndex = index;
    // this.deleteProject.id= this.projects[index].id;
    // this.deleteProject.name= this.projects[index].name;
    // this.deleteProject.dateStart= this.projects[index].dateStart.slice(0,10);
    // this.deleteProject.teamSize= this.projects[index].teamSize;
  }


}

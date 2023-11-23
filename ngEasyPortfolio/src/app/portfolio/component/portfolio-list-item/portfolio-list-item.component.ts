import { Component, Input, OnInit } from '@angular/core';
import { Portfolio} from '../../model/portfolio/portfolio.interface';
import { ActivatedRoute } from '@angular/router';
import { PortfolioService } from '../../services/portfolio.service';
import { LoginUser } from 'src/app/login/login-user.interface';
import { Skill } from '../skill/skill.interface';
import { JWTTokenService } from 'src/app/services/JWTToken.service';


@Component({
  selector: 'app-portfolio-list-item',
  templateUrl: './portfolio-list-item.component.html',
  styleUrls: ['./portfolio-list-item.component.scss']
})
export class PortfolioListItemComponent implements OnInit {
  table: string = 'portfolios';
  portfolioId!:number;
  portfolio: Portfolio = {
        id: 0,
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
    loginUser: LoginUser ={
      email:"",
      token: "",

    } 
    legend!: string;
    skillEdit: Skill = {
      id:-1,
      title: "",
      description: "",
      portfolioId: 0
    };

    skillForm: boolean = false;

  constructor(
      private route: ActivatedRoute,
      private portfolioService: PortfolioService,
      private jwtTokenService : JWTTokenService
    ){};

  ngOnInit(): void {
      this.portfolioId =  this.portfolioService.getId(this.route.snapshot.paramMap.get('id'));
      this.loginUser.email = this.jwtTokenService.getUser();      

      this.portfolioService.getPortfolioById(this.table, this.portfolioId)
      .subscribe({
        next:(response:Portfolio) => { this.portfolio = response }, 
        error: (err:Error) => console.log("Error portfolioById")
      })
      ;
  }
 
  public onAddSkill = () => {
    this.legend = "Ajouter une compétence"
    this.skillForm = true;
  }

  public onSubmit = ()=>{
    // hide the form
    this.skillForm = false; 
    this.skillEdit.portfolioId = this.portfolioId;  
    this.portfolioService.addSkill('skills' , this.skillEdit)
    .subscribe({
      next:(data)=>{
        // Ajouter skillEdit a skills [] pour affichage
        this.portfolio.skills.push(this.skillEdit);
      },
      error:(err:Error)=>{
        console.log("**error adding skill**");
      }
    });
  }

  // onEditClick(event: any) {
    // console.log (this.projects[index].dateStart.slice(0,10));  **  formater date pour affichage html
    // console.log(index);
    
    // this.currentUser.id= this.users[index].id;
    // this.editProject.name= this.projects[index].name;
    // this.editProject.dateStart= this.projects[index].dateStart.slice(0,10);
    // this.editProject.teamSize= this.projects[index].teamSize;

    // this.getUsers(this.table, this.currentUser.id);

 
    // this.currentIndex=index;
  // }

  // onDeleteClick(event:any){
    // this.deleteIndex = index;
    // this.deleteProject.id= this.projects[index].id;
    // this.deleteProject.name= this.projects[index].name;
    // this.deleteProject.dateStart= this.projects[index].dateStart.slice(0,10);
    // this.deleteProject.teamSize= this.projects[index].teamSize;

  


}

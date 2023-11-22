import { Component, Input, OnInit } from '@angular/core';
import { Portfolio} from '../../model/portfolio/portfolio.interface';
import { ActivatedRoute } from '@angular/router';
import { PortfolioService } from '../../services/portfolio.service';
import { LoginUser } from 'src/app/login/login-user.interface';
import { SessionStorageService } from 'src/app/services/session-storage.service';
import { Skill } from '../skill/skill.interface';
import { SkillService } from '../../services/skill.service';


@Component({
  selector: 'app-portfolio-list-item',
  templateUrl: './portfolio-list-item.component.html',
  styleUrls: ['./portfolio-list-item.component.scss']
})
export class PortfolioListItemComponent implements OnInit {
  table: string = 'portfolios';
  portfolioId: string|any;
  portfolio: Portfolio ={
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
      login:"",
      token: "",
      conButton:"Déconnexion"
    } 
    legend!: string;
    skillEdit: Skill = {
      id:null,
      title: "",
      description: "",
      portfolioId: 0
    };

    skillForm: boolean = false;

  constructor(
      private route: ActivatedRoute,
      private portfolioService: PortfolioService,
      private skillService: SkillService,
      private storageService: SessionStorageService
    ){};

  
  ngOnInit(): void {
      this.portfolioId =  this.route.snapshot.paramMap.get('id');
      this.loginUser.login = this.storageService.getLogin();
      this.loginUser.conButton = this.storageService.getConButton();  
      if( this.loginUser.login != ""){
        if(this.portfolioId != null){ 
          this.getPortfolioById(this.table, this.portfolioId);
      }
    }
  }

  getPortfolioById = (table:string, id:number)=> {
    this.portfolioService.getPortfolioById(table, id)
    .subscribe({
      next:(response:Portfolio) => { 
        this.portfolio = response;
       }, 
      error: (err:Error) => console.log("Error portfolioById"),
      complete: ()=> console.log(this.portfolio.title)
    })
  }

  public onAddSkill = () => {
    this.legend = "Ajouter une compétence"
    this.skillForm = true;
  }

  public onSubmit = ()=>{
    // hide the form
    this.skillForm = false; 
    this.skillEdit.portfolioId = this.portfolioId;  
    this.skillService.add(this.skillEdit)
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

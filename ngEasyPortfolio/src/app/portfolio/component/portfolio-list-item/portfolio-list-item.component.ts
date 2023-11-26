import { Component, Input, OnInit } from '@angular/core';
import { Portfolio} from '../../model/portfolio/portfolio.interface';
import { ActivatedRoute } from '@angular/router';
import { PortfolioService } from '../../services/portfolio.service';
import { Skill } from '../skill/skill.interface';
import { JWTTokenService } from 'src/app/services/JWTToken.service';
import { Experience } from '../experience/experience.interface';
import { Education } from '../education/education.interface';


@Component({
  selector: 'app-portfolio-list-item',
  templateUrl: './portfolio-list-item.component.html',
  styleUrls: ['./portfolio-list-item.component.scss']
})
export class PortfolioListItemComponent implements OnInit {
  table: string = 'portfolios';
  portfolioId!:number;
  legend!: string; // form legend
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
    
    
    skillEdit: Skill = {
      id:-1,
      title: "",
      description: "",
      portfolioId: 0
    };
    skillForm: boolean = false; // display or hide form

    experienceEdit: Experience = {
      id: -1,
      title: "",
      company:"",
      description: "",
      startDate: "",
      endDate:"",
      portfolioId:0
    };
    experienceForm: boolean = false; // display or hide form

    educationEdit: Education = {
        id: -1,
        training: "",
        school: "",
        degree: "",
        startDate: new Date("1970-01-01"),
        endDate: new Date,
        description: "",
        portfolioId: 0
  
    };
    educationForm: boolean = false; // display or hide form
    
  constructor(
      private route: ActivatedRoute,
      private portfolioService: PortfolioService,
      private jwtTokenService : JWTTokenService
    ){};

  ngOnInit(): void {
      this.portfolioId =  this.portfolioService.getId(this.route.snapshot.paramMap.get('id'));     

      this.portfolioService.getPortfolioById(this.table, this.portfolioId)
      .subscribe({
        next:(response:Portfolio) => { this.portfolio = response }, 
        error: (err:Error) => console.log("Error portfolioById")
      });
      
  }

  public onAddSkill = () => {
    this.legend = "Ajouter une compétence"
    this.skillForm = true;
  }
  public onAddExperience = () => {
    this.legend = "Ajouter une expérience"
    this.experienceForm = true;
  }
  public onAddEducation = () => {
    this.legend = "Ajouter une expérience"
    this.educationForm = true;
  }

  public onSubmitEducation = ()=>{
    console.log(this.educationEdit.startDate);
    let testDate = new Date();
    console.log(testDate, "**");
    
    
    // // hide the form
    this.educationForm = false; 
    this.educationEdit.portfolioId = this.portfolioId;  
    this.portfolioService.addEducation('educations' , this.educationEdit)
    .subscribe({
      next:(data)=>{
        // Ajouter educationEdit a educations [] pour affichage
        this.portfolio.educations.push(this.educationEdit);
      },
      error:(err:Error)=>{
        console.log("**error adding education**");
      }
    });
  }

  public onSubmitExperience = ()=>{
    // hide the form
    this.experienceForm = false; 
    this.experienceEdit.portfolioId = this.portfolioId;  
    this.portfolioService.addExperience('experiences' , this.experienceEdit)
    .subscribe({
      next:(data)=>{
        // Ajouter skillEdit a skills [] pour affichage
        this.portfolio.experiences.push(this.experienceEdit);
      },
      error:(err:Error)=>{
        console.log("**error adding experience**");
      }
    });
  }

  onDeleteExperience = (experienceId : number, index: number):void => {
    this.portfolioService.deleteExperience("experiences" , experienceId)
    .subscribe({
    next:( )=> {
      this.portfolio.experiences.splice(index,1)
      },
    error:(err:Error)=>{ console.log("Error while deleting experience.");
        }
    }) 
  }

  public onSubmitSkill = ()=>{
    // hide the form
    this.skillForm = false; 
    this.skillEdit.portfolioId = this.portfolioId;  
    this.portfolioService.addSkill('skills' , this.skillEdit)
    .subscribe({
      next:(data)=>{
        // Add skillEdit to skills [], display purpose
        this.portfolio.skills.push(this.skillEdit);
      },
      error:(err:Error)=>{
        console.log("**error adding skill**");
      }
    });
  }

  onDeleteSkill = (skillId : number, index: number):void => {
    this.portfolioService.deleteSkill("skills" , skillId)
    .subscribe({
    next:( )=> {
      this.portfolio.skills.splice(index,1)
      },
    error:(err:Error)=>{ console.log("Error while deleting skill.");
        }
    }) 
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

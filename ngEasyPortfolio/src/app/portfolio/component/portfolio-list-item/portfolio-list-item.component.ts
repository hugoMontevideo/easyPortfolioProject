import { Component, ElementRef, OnInit, Renderer2, ViewChild } from '@angular/core';
import { Portfolio} from '../../model/portfolio/portfolio.interface';
import { ActivatedRoute } from '@angular/router';
import { PortfolioService } from '../../services/portfolio.service';
import { Project } from '../project/project.interface';
import { Skill } from '../skill/skill.interface';
import { Education } from '../education/education.interface';
import { Experience } from '../experience/experience.interface';
import { JWTTokenService } from 'src/app/services/JWTToken.service';
import { HttpErrorResponse } from '@angular/common/http';
import { BoardManager } from './board-manager.interface';


@Component({
  selector: 'app-portfolio-list-item',
  templateUrl: './portfolio-list-item.component.html',
  styleUrls: ['./portfolio-list-item.component.scss']
})
export class PortfolioListItemComponent implements OnInit {
  @ViewChild('collapseOne') collapseOne! : ElementRef;
  @ViewChild('collapseTwo') collapseTwo! : ElementRef;
  @ViewChild('collapseThree') collapseThree! : ElementRef;
  @ViewChild('collapseFour') collapseFour! : ElementRef;
  inputError?: string;
  collapseOnel!: HTMLDivElement;
  collapseTwol!: HTMLDivElement;
  collapseThreel!: HTMLDivElement;
  collapseFourl!: HTMLDivElement;
  isProtected = true;  // dark color on secondary nav
  portfolio: Portfolio = {
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
                    user: {
                      id: 0,
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
                
  constructor(
      private route: ActivatedRoute,
      private portfolioService: PortfolioService,
      private jwtService: JWTTokenService,
      private renderer: Renderer2
    ){};

  ngOnInit(): void {
    this.portfolio.id =  this.portfolioService.getId(this.route.snapshot.paramMap.get('id'));
    this.portfolioService.getPortfolioById( this.portfolio.id)
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
  ngAfterViewInit(){    
    this.collapseOnel = this.collapseOne.nativeElement; 
    this.collapseTwol = this.collapseTwo.nativeElement; 
    this.collapseThreel = this.collapseThree.nativeElement; 
    this.collapseFourl = this.collapseFour.nativeElement; 
  }
  
  boardManager: BoardManager = {
    isShowingHome:true,
    isShowingProjects : false,
    isShowingSkills : false,
    isShowingEducs : false,
    isShowingExpers : false,
    isPortfolioFormShowing : false
  }

  onProjectButton =():void => {
    this.boardManager = this.portfolioService.resetBoardManager();
    this.boardManager.isShowingProjects = true;
  }

  onSkillButton = ():void => {
    this.boardManager = this.portfolioService.resetBoardManager();
    this.boardManager.isShowingSkills = true;
  }
  onEducationButton = () => {
    this.boardManager = this.portfolioService.resetBoardManager();
    this.boardManager.isShowingEducs = true;
  }
  onExperienceButton(){
    this.boardManager = this.portfolioService.resetBoardManager();
    this.boardManager.isShowingExpers = true;
  }
  public onEditPortfolio = () => {
    this.boardManager = this.portfolioService.resetBoardManager();
    this.boardManager.isPortfolioFormShowing = true;
    this.renderer.removeClass(this.collapseOnel, 'show');  // bs class on accordion
    this.renderer.removeClass(this.collapseTwol, 'show');
    this.renderer.removeClass(this.collapseThreel, 'show');
    this.renderer.removeClass(this.collapseFourl, 'show');
    this.boardManager.isShowingHome=true; 
  }

  handleProjectsChanged = (updatedProjects:Project[]) => {  // EventEmitter
    this.portfolio.projects = updatedProjects;
  }
  handleSkillsChanged = (updatedSkills:Skill[]) => {  // EventEmitter
    this.portfolio.skills = updatedSkills;
  }
  handleEducationsChanged = (updatedEducations:Education[]) => {  // EventEmitter
    this.portfolio.educations = updatedEducations;
  }
  handleExperiencesChanged = (updatedExperiences:Experience[]) => {  // EventEmitter
    this.portfolio.experiences = updatedExperiences;
  }

  public onCloseModalForm = () => {
    this.boardManager.isPortfolioFormShowing = false;
  }

  onDeletePortfolio = () :void => {
    this.portfolioService.deletePortfolio( this.portfolio.id )
      .subscribe({
       next:( )=> {
           this.portfolioService.getPortfolioById(this.portfolio.id) // refresh projects[]
              .subscribe({
                   next: (data:Portfolio) => { 
                           this.portfolio = data;
                         },
                   error: (_error: Error)=>{
                       console.log("**error Getting Projects**"); 
                     }
               });
       },
      error:(err:Error)=>{ console.log("Error while deleting education.");
         }
      }) 
 };

 public onSubmitPortfolio = ()=>{    
  this.portfolioService.savePortfolio(this.portfolio)
   .subscribe({
     next:(data)=>{
         this.boardManager.isPortfolioFormShowing = false;  // hide the form
         this.portfolioService.getPortfolioById(this.portfolio.id) // refresh portfolios[]
           .subscribe({
               next: (data:Portfolio) => { 
                       this.portfolio = data;
                       console.log(data);
                       
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

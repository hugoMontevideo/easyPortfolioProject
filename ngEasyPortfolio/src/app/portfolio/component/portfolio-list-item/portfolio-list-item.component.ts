import { Component, OnInit } from '@angular/core';
import { Portfolio} from '../../model/portfolio/portfolio.interface';
import { ActivatedRoute } from '@angular/router';
import { PortfolioService } from '../../services/portfolio.service';
import { Project } from '../project/project.interface';
import { Skill } from '../skill/skill.interface';
import { Education } from '../education/education.interface';
import { Experience } from '../experience/experience.interface';
import { environment } from 'src/environments/environment';


@Component({
  selector: 'app-portfolio-list-item',
  templateUrl: './portfolio-list-item.component.html',
  styleUrls: ['./portfolio-list-item.component.scss']
})
export class PortfolioListItemComponent implements OnInit {
  isShowingHome = true;
  isShowingProjects = false;
  isShowingSkills = false;
  isShowingEducs = false;
  isShowingExpers = false;

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

  onProjectButton =():void => {
    this.isShowingProjects = !this.isShowingProjects;
    this.isShowingHome=!this.isShowingProjects;
    this.isShowingSkills = false;
    this.isShowingEducs = false;
    this.isShowingExpers = false;
  }
  onSkillButton = ():void => {
    this.isShowingSkills = !this.isShowingSkills;
    this.isShowingHome=!this.isShowingSkills;

    this.isShowingProjects = false;
    this.isShowingEducs = false;
    this.isShowingExpers = false;
  }
  onEducationButton = () => {
    this.isShowingEducs = !this.isShowingEducs;
    this.isShowingHome=!this.isShowingEducs;
    
    this.isShowingProjects = false;
    this.isShowingSkills = false;
    this.isShowingExpers = false;
  }
  onExperienceButton(){
    this.isShowingExpers = !this.isShowingExpers;
    this.isShowingHome=!this.isShowingExpers;

    this.isShowingProjects = false;
    this.isShowingSkills = false;
    this.isShowingEducs = false;
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


}

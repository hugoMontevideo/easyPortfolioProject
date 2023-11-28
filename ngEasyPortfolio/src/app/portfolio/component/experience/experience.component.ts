import { Component, Input, OnChanges } from '@angular/core';
import { Experience } from './experience.interface';
import { PortfolioService } from '../../services/portfolio.service';
import { ExperienceService } from '../../services/experience.service';

@Component({
  selector: 'app-experience',
  templateUrl: './experience.component.html',
  styleUrls: ['./experience.component.scss']
})
export class ExperienceComponent {

  @Input() experiences!:Experience[];
  @Input() portfolioId!: number;

  legend: string = "Ajouter";

  newExperience: Experience = {
    id: -1,
    title: "",
    company:"",
    description: "",
    startDate: "",
    endDate:"",
    portfolioId: this.portfolioId
  };
  isExperienceFormShowing: boolean = false; // display or hide form

  constructor( private experienceService: ExperienceService ){}
  
  ngOnChanges(){
    this.newExperience.portfolioId = this.portfolioId;
  }

  public onAddExperience = () => {
    this.legend = "Ajouter une expérience"
    this.isExperienceFormShowing = true;
  }

  public onSubmitExperience = ()=>{
    // hide the form
    this.isExperienceFormShowing = false; 
    this.newExperience.portfolioId = this.portfolioId;  
    this.experienceService.addExperience('experiences' , this.newExperience)
    .subscribe({
      next:(data)=>{
        // Ajouter skillEdit a skills [] pour affichage
        this.experiences.push(this.newExperience);
      },
      error:(err:Error)=>{
        console.log("**error adding experience**");
      }
    });
  }

  onDeleteExperience = (experienceId : number, index: number):void => {
    this.experienceService.deleteExperience("experiences" , experienceId)
    .subscribe({
    next:( )=> {
      this.experiences.splice(index,1)
      },
    error:(err:Error)=>{ console.log("Error while deleting experience.");
        }
    }) 
  }

}

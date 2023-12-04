import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { Experience } from './experience.interface';
import { ExperienceService } from '../../services/experience.service';
import { ExperienceModel } from './experience-model';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-experience',
  templateUrl: './experience.component.html',
  styleUrls: ['./experience.component.scss']
})
export class ExperienceComponent {

  @Input() experiences!:ExperienceModel[];
  @Input() portfolioId!: number;

  legend: string = "Ajouter";
  inputError!: string;
  newExperience: Experience = {
    id: -1,
    title: "",
    company:"",
    description: "",
    startDate: new Date("1970-01-01"),
    endDate: new Date("1970-01-01"),
    portfolioId: this.portfolioId
  };
  isExperienceFormShowing: boolean = false; // display or hide form

  constructor( 
          private experienceService: ExperienceService 
              ){}
  
  ngOnChanges(change: SimpleChanges ){
    this.newExperience.portfolioId = this.portfolioId;
  }

  public onCloseModalForm = () => {
    this.isExperienceFormShowing = false;
    this.newExperience = this.experienceService.resetNewExperience(this.newExperience.portfolioId);
  }

  public onAddExperience = () => {
    this.legend = "Ajouter une expérience"
    this.isExperienceFormShowing = true;
    this.experienceService.tempData = "add";
  }

  public onEditExperience = (index: number) => {
    this.legend = "Modifier une expérience professionnelle"
    this.isExperienceFormShowing = true;
    this.newExperience = this.experiences[index];
    this.newExperience.portfolioId = this.portfolioId;
    this.experienceService.tempData = "edit";  
  }

  public onSubmitExperience = ()=>{
    // hide the form
    this.experienceService.saveExperience('experiences' , this.newExperience)
    .subscribe({
      next:(data)=>{
        this.isExperienceFormShowing = false;  
        // Ajouter skillEdit a skills [] pour affichage
        this.experiences = 
          this.experienceService.refreshExperiences(this.experiences, 
                                                    new ExperienceModel(
                                                    data.id,
                                                    data.title,
                                                    data.company,
                                                    data.description,
                                                    data.startDate,
                                                    data.endDate,
                                                    this.newExperience.portfolioId
                                                  )
                                                );
        this.newExperience = this.experienceService.resetNewExperience(this.newExperience.portfolioId);
      },
      error:(_error)=>{
        console.log("**error Adding Experience**");
        if(_error instanceof HttpErrorResponse ) {
          this.inputError = _error.error.title;
        } 
      }
    });
  }

  onDeleteExperience = (experienceId : number, index: number):void => {
    this.experienceService.deleteExperience("experiences" , experienceId)
    .subscribe({
      next:( )=> {
            this.experiences.splice(index,1);
            this.newExperience = this.experienceService.resetNewExperience(this.portfolioId);
        },
      error:(_error:Error)=>{ console.log("Error while deleting experience.");}
    }) 
  }

}

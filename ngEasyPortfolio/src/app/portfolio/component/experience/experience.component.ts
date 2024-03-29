import { Component, EventEmitter, Input, OnChanges, Output, SimpleChanges } from '@angular/core';
import { Experience } from './experience.interface';
import { ExperienceService } from '../../services/experience.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-experience',
  templateUrl: './experience.component.html',
  styleUrls: ['./experience.component.scss']
})
export class ExperienceComponent {

  @Input() experiences:Experience[] = [];
  @Input() portfolioId?: number;
  @Output() experiencesChanged = new EventEmitter<Experience[]>();
  legend: string = "";
  inputError?: string;

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

  constructor( private experienceService: ExperienceService){};
  
  ngOnChanges(change: SimpleChanges ){
    this.newExperience.portfolioId = this.portfolioId;
  }

  public onCloseModalForm = () => {
    this.isExperienceFormShowing = false;
    this.newExperience = this.experienceService.resetNewExperience(this.newExperience.portfolioId);
  }

  public onEditExperience = (index: number) => {
    this.legend = "Modifier une expérience professionnelle"
    this.isExperienceFormShowing = true;
    this.newExperience = this.experiences[index];
    this.newExperience.portfolioId = this.portfolioId; 
  }

  public onAddExperience = () => {
    this.legend = "Ajouter une expérience"
    this.isExperienceFormShowing = true;
    this.experienceService.add(this.newExperience)
    .subscribe({
      next:(data)=>{
        this.newExperience.id = data.id; 
        this.newExperience.title = data.training; 
        this.experienceService.getExperiences(this.newExperience.portfolioId) // refresh projects[]
          .subscribe({
              next: (data:Experience[]) => { 
                            this.experiences = data;
                            this.experiencesChanged.emit(this.experiences); 
                          },
              error: (err:Error)=>{console.error("**error Getting experiences**");} //TODO *******
            });
      },
      error:(_error)=>{
        console.error("**error Adding Experience**");
        if(_error instanceof HttpErrorResponse ) {
          this.inputError = _error.error.training;
        } 
      }
    });
  }

  public onSubmitExperience = ()=>{
    // hide the form
    this.experienceService.saveExperience(this.newExperience)
    .subscribe({
      next:(data)=>{
        this.isExperienceFormShowing = false;  
        this.newExperience = this.experienceService.resetNewExperience(this.newExperience.portfolioId);
        this.experienceService.getExperiences(this.newExperience.portfolioId) // refresh projects[]
            .subscribe({
                next: (data:Experience[]) => { 
                          this.experiences = data;
                          this.experiencesChanged.emit(this.experiences);
                         },
                error: (err:Error)=>{console.error("**error Getting educations**");} //TODO *******
              });
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
    this.experienceService.deleteExperience( experienceId )
      .subscribe({
        next:( )=> {
          this.experienceService.getExperiences(this.newExperience.portfolioId) // refresh educations[]
            .subscribe({
              next: (data:Experience[]) => { 
                      this.experiences = data;
                      this.experiencesChanged.emit(this.experiences); 
                    },
              error: (_error)=>{
                  console.log("**error Getting Projects**"); 
                }
            });
          },
        error:(err:Error)=>{ console.log("Error while deleting education.");}
      });
  }

}

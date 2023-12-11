import { Component, EventEmitter, Input, OnChanges, Output, SimpleChanges } from '@angular/core';
import { Education } from './education.interface';
import { EducationService } from '../../services/education.service';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-education',
  templateUrl: './education.component.html',
  styleUrls: ['./education.component.scss']
})
export class EducationComponent {

  @Input() educations:Education[] = [];
  @Input() portfolioId?: number;
  @Output() educationsChanged = new EventEmitter<Education[]>();
  legend: string = "";
  inputError?: string;

  newEducation: Education = {
    id: -1,
    training: "",
    school: "",
    degree: "",
    startDate: new Date("1970-01-01"),
    endDate: new Date("1970-01-01"),
    description: "",
    portfolioId: this.portfolioId
};
  isEducationFormShowing: boolean = false; // display or hide form

  constructor( private educationService: EducationService ){}

  ngOnChanges(change: SimpleChanges){
    this.newEducation.portfolioId = this.portfolioId;
  }

  public onCloseModalForm = () => {
    this.isEducationFormShowing = false;
    this.newEducation = this.educationService.resetNewEducation(this.newEducation.portfolioId);
  }

  public onEditEducation = (index: number) => {
    this.legend = "Modifier une compétence"
    this.isEducationFormShowing = true;
    this.newEducation = this.educations[index];
    this.newEducation.portfolioId = this.portfolioId;
  }

  public onAddEducation = () => {
    this.legend = "Ajouter une expérience"
    this.isEducationFormShowing = true;
    this.educationService.add(this.newEducation)
    .subscribe({
      next:(data)=>{
        this.newEducation.id = data.id; 
        this.newEducation.training = data.training; 
        this.educationService.getEducations(this.newEducation.portfolioId) // refresh projects[]
          .subscribe({
              next: (data:Education[]) => { 
                          this.educations = data;
                          this.educationsChanged.emit(this.educations);
                        },
              error: (err:Error)=>{console.error("**error Getting educations**");} //TODO *******
            });
      },
      error:(_error)=>{
        console.log("**error Adding Education**");
        if(_error instanceof HttpErrorResponse ) {
          this.inputError = _error.error.training;
        } 
      }
    });
  }

  public onSubmitEducation = ()=>{
    // // hide the form
    this.newEducation.portfolioId = this.portfolioId;  
    this.educationService.saveEducation(this.newEducation)
    .subscribe({
      next:(data)=>{
          this.isEducationFormShowing = false;  // hide the form
          this.newEducation = this.educationService.resetNewEducation(this.portfolioId);
          this.educationService.getEducations(this.newEducation.portfolioId) // refresh projects[]
            .subscribe({
                next: (data:Education[]) => { 
                          this.educations = data;
                          this.educationsChanged.emit(this.educations);
                         },
                error: (err:Error)=>{console.error("**error Getting educations**");} //TODO *******
              });
        },
      error:(_error)=>{
        console.error("**error updating Education**");
        if(_error instanceof HttpErrorResponse ) {
          this.inputError = _error.error.training;
        } 
      }
    });
  }

  onDeleteEducation = (educationId : number, index: number):void => {
    this.educationService.deleteEducation( educationId)
    .subscribe({
    next:( )=> {
      this.educationService.getEducations(this.newEducation.portfolioId) // refresh educations[]
        .subscribe({
          next: (data:Education[]) => { 
                  this.educations = data;
                  this.educationsChanged.emit(this.educations); 
                },
          error: (_error)=>{
              console.log("**error Getting Projects**"); 
            }
        });
       },
    error:(err:Error)=>{ console.log("Error while deleting education.");}
    }) 
  }


  
 


}

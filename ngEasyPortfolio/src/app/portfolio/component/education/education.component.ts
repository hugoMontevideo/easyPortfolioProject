import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { Education } from './education.interface';
import { EducationService } from '../../services/education.service';
import { EducationModel } from './education-model';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-education',
  templateUrl: './education.component.html',
  styleUrls: ['./education.component.scss']
})
export class EducationComponent {

  @Input() educations!:EducationModel[];
  @Input() portfolioId!: number;
  legend: string = "Ajouter";
  inputError!: string;

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

  public onAddEducation = () => {
    this.legend = "Ajouter une expérience"
    this.isEducationFormShowing = true;
    this.educationService.tempData = "add";
  }

  public onEditSkill = (index: number) => {
    this.legend = "Modifier une compétence"
    this.isEducationFormShowing = true;
    this.newEducation = this.educations[index];
    this.newEducation.portfolioId = this.portfolioId;
    this.educationService.tempData = "edit";  
  }


  public onSubmitEducation = ()=>{
    // // hide the form
    this.newEducation.portfolioId = this.portfolioId;  
    this.educationService.saveEducation('educations' , this.newEducation)
    .subscribe({
      next:(data)=>{
        this.isEducationFormShowing = false;      
         // Add educationModel to educations[], display purpose
         this.educations = this.educationService.refreshSkills(this.educations,
                                                       new EducationModel( 
                                                         data.id,
                                                         data.training,
                                                         data.school,
                                                         data.degree,
                                                         data.startDate,
                                                         data.endDate,
                                                         data.description,
                                                         this.newEducation.portfolioId ) 
                                                       );
         this.newEducation = this.educationService.resetNewEducation(this.newEducation.portfolioId);
       },
       error:(_error)=>{
         console.log("**error Adding Education**");
         if(_error instanceof HttpErrorResponse ) {
           this.inputError = _error.error.training;
         } 
       }
     });
  }

  onDeleteEducation = (educationId : number, index: number):void => {
    this.educationService.deleteEducation("educations" , educationId)
    .subscribe({
    next:( )=> {
      this.educations.splice(index,1)
      },
    error:(err:Error)=>{ console.log("Error while deleting education.");}
    }) 
  }


  



}

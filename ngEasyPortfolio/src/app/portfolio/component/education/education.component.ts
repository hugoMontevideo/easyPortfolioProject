import { Component, Input, OnChanges } from '@angular/core';
import { Education } from './education.interface';
import { PortfolioService } from '../../services/portfolio.service';
import { EducationService } from '../../services/education.service';

@Component({
  selector: 'app-education',
  templateUrl: './education.component.html',
  styleUrls: ['./education.component.scss']
})
export class EducationComponent {

  @Input() educations!:Education[];
  @Input() portfolioId!: number;

  legend: string = "Ajouter";

  constructor( private educationService: EducationService ){}

  newEducation: Education = {
    id: -1,
    training: "",
    school: "",
    degree: "",
    startDate: new Date("1970-01-01"),
    endDate: new Date,
    description: "",
    portfolioId: this.portfolioId
};
  isEducationFormShowing: boolean = false; // display or hide form

  ngOnChanges(){
    this.newEducation.portfolioId = this.portfolioId;
  }

  

  public onAddEducation = () => {
    this.legend = "Ajouter une expérience"
    this.isEducationFormShowing = true;
  }

  public onSubmitEducation = ()=>{
    // // hide the form
    this.isEducationFormShowing = false; 
    this.newEducation.portfolioId = this.portfolioId;  
    this.educationService.addEducation('educations' , this.newEducation)
    .subscribe({
      next:(data: Education)=>{
        // Ajouter educationEdit a educations [] pour affichage
        this.educations.push(this.newEducation);
      },
      error:(err:Error)=>{
        console.log("**error adding education**");
      }
    });
  }

  onDeleteEducation = (educationId : number, index: number):void => {
    this.educationService.deleteEducation("educations" , educationId)
    .subscribe({
    next:( )=> {
      this.educations.splice(index,1)
      },
    error:(err:Error)=>{ console.log("Error while deleting education.");
        }
    }) 
  }


  



}

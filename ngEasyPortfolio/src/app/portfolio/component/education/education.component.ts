import { Component, Input, OnChanges } from '@angular/core';
import { Education } from './education.interface';
import { PortfolioService } from '../../services/portfolio.service';

@Component({
  selector: 'app-education',
  templateUrl: './education.component.html',
  styleUrls: ['./education.component.scss']
})
export class EducationComponent {

  @Input() educations!:Education[];
  @Input() portfolioId!: number;

  legend: string = "Ajouter";

  constructor( private portfolioService: PortfolioService ){}
  currentEducation: Education = {
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
    this.currentEducation.portfolioId = this.portfolioId;
  }

  

  public onAddEducation = () => {
    this.legend = "Ajouter une expérience"
    this.isEducationFormShowing = true;
  }

  public onSubmitEducation = ()=>{
    // // hide the form
    this.isEducationFormShowing = false; 
    this.currentEducation.portfolioId = this.portfolioId;  
    this.portfolioService.addEducation('educations' , this.currentEducation)
    .subscribe({
      next:(data: Education)=>{
        // Ajouter educationEdit a educations [] pour affichage
        this.educations.push(this.currentEducation);
      },
      error:(err:Error)=>{
        console.log("**error adding education**");
      }
    });
  }

  onDeleteEducation = (educationId : number, index: number):void => {
    this.portfolioService.deleteEducation("educations" , educationId)
    .subscribe({
    next:( )=> {
      this.educations.splice(index,1)
      },
    error:(err:Error)=>{ console.log("Error while deleting education.");
        }
    }) 
  }


  



}

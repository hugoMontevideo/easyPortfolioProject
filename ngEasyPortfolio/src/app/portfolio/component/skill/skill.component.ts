import { Component, Input, OnChanges } from '@angular/core';
import { Skill } from './skill.interface';
import { PortfolioService } from '../../services/portfolio.service';


@Component({
  selector: 'app-skill',
  templateUrl: './skill.component.html',
  styleUrls: ['./skill.component.scss']
})
export class SkillComponent {
  @Input() skills!:Skill[];
  @Input() portfolioId!: number;

  legend: string = "Ajouter";

  currentSkill: Skill = {
    id:-1,
    title: "",
    description: "",
    portfolioId: this.portfolioId
  };
  isSkillFormShowing: boolean = false; // display or hide form

  constructor(
    private portfolioService: PortfolioService      
              ){}

    ngOnChanges(){
      this.currentSkill.portfolioId = this.portfolioId;
    }

  onDeleteSkill = (skillId : number, index: number):void => {
    this.portfolioService.deleteSkill("skills" , skillId)
    .subscribe({
    next:( )=> {
      this.skills.splice(index,1)
      },
    error:(err:Error)=>{ console.log("Error while deleting skill.");
        }
    }) 
  }

  public onAddSkill = () => {
    this.legend = "Ajouter une compétence"
    this.isSkillFormShowing = true;
  }

  public onSubmitSkill = ()=>{
    // hide the form
    this.isSkillFormShowing = false; 
    // console.log(this.currentSkill);
     
    this.portfolioService.addSkill('skills' , this.currentSkill)
    .subscribe({
      next:(data)=>{
        // Add skillEdit to skills [], display purpose
        this.skills.push(this.currentSkill);
      },
      error:(err:Error)=>{
        console.log("**error adding skill**");
      }
    });
  }

}

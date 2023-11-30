import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { Skill } from './skill.interface';
import { SkillService } from '../../services/skill.service';
import { SkillModel } from './skill-model';

@Component({
  selector: 'app-skill',
  templateUrl: './skill.component.html',
  styleUrls: ['./skill.component.scss']
})
export class SkillComponent {
  @Input() skills!:SkillModel[];
  @Input() portfolioId!: number;

  legend: string = "Ajouter";

  newSkill: Skill = {
    id:-1,
    title: "",
    description: "",
    portfolioId: this.portfolioId
  };
  isSkillFormShowing: boolean = false; // display or hide form

  constructor(  
          private skillService: SkillService      
              ){}

  ngOnChanges( change: SimpleChanges){
    this.newSkill.portfolioId = this.portfolioId;
  }

  onDeleteSkill = (skillId : number, index: number):void => {
    this.skillService.deleteSkill("skills" , skillId)
    .subscribe({
    next:( )=> {
          this.skills.splice(index,1);
          this.newSkill = this.skillService.resetNewSkill(this.portfolioId);
      },
    error:(err:Error)=>{ console.log("Error while deleting skill.");
        }
    }) 
  }

  public onAddSkill = () => {
    this.legend = "Ajouter une compétence"
    this.isSkillFormShowing = true;
  }

  public onEditSkill = (index: number) => {
    this.legend = "Modifier une compétence"
    this.isSkillFormShowing = true;
    this.newSkill = this.skills[index];
    
    // console.log(this.skills[index]); 
  }

  public onSubmitSkill = ()=>{
    // hide the form
    this.isSkillFormShowing = false;      
    this.skillService.addSkill('skills' , this.newSkill)
    .subscribe({
      next:(data)=>{
        // Add skillModel to skills [], display purpose
        this.skills.push(new SkillModel(
          data.id,
          data.title,
          data.description,
          this.newSkill.portfolioId
        ));
        this.newSkill = this.skillService.resetNewSkill(this.newSkill.portfolioId);
      },
      error:(err:Error)=>{
        console.log("**error adding skill**");
      }
    });
  }

}

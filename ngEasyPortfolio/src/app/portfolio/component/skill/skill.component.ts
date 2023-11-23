import { Component, Input } from '@angular/core';
import { Skill } from './skill.interface';
import { PortfolioService } from '../../services/portfolio.service';


@Component({
  selector: 'app-skill',
  templateUrl: './skill.component.html',
  styleUrls: ['./skill.component.scss']
})
export class SkillComponent {

  @Input() skill!:Skill;

  constructor(private portfolioService: PortfolioService,
            
              ){}

  onDelete = (skillId : number):void => {
    console.log(skillId);
    
    this.portfolioService.deleteSkill("skills" , skillId)
    .subscribe({
      next:( )=> { },
      error:(err:Error)=>{ console.log("Error while deleting skill.");
          }
    }) 
  }


}

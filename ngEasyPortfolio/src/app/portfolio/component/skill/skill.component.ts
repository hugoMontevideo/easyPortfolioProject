import { Component, EventEmitter, Input, Output } from '@angular/core';
import { Skill } from './skill.interface';
import { PortfolioService } from '../../services/portfolio.service';


@Component({
  selector: 'app-skill',
  templateUrl: './skill.component.html',
  styleUrls: ['./skill.component.scss']
})
export class SkillComponent {

  @Input() skill!:Skill;
  @Input() i!: number;
  @Output() index2Send = new EventEmitter<string>();

  constructor(private portfolioService: PortfolioService,
            
              ){}

  onDelete = (skillId : number, index: number):void => {
    console.log(skillId + ' ' + index);
    
    // this.portfolioService.deleteSkill("skills" , skillId)
    // .subscribe({
    //   next:( )=> { },
    //   error:(err:Error)=>{ console.log("Error while deleting skill.");
    //       }
    // }) 
  }

  onEmitData = () => {
    let indexString:string = "hello";
    this.index2Send.emit(indexString);
  }


}

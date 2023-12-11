import { Component, Input, SimpleChanges } from '@angular/core';
import { Skill } from '../../component/skill/skill.interface';
import { SkillService } from '../../services/skill.service';

@Component({
  selector: 'app-board-skill',
  templateUrl: './board-skill.component.html',
  styleUrls: ['./board-skill.component.scss']
})
export class BoardSkillComponent {
  @Input() skills:Skill[]= [];
  @Input() portfolioId?:number;

  constructor(private skillService: SkillService){};

  ngOnChanges(changes: SimpleChanges){  

    this.skillService.getSkills(this.portfolioId) // refresh projects []
          .subscribe({
              next: (data:Skill[]) => { this.skills = data },
              error: (err:Error)=>{console.error("**error Getting Skills**");} //TODO *******
            });    
  }

}

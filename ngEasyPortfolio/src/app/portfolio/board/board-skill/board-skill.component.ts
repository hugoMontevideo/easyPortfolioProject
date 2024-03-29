import { Component, Input, SimpleChanges } from '@angular/core';
import { Skill } from '../../component/skill/skill.interface';
import { SkillService } from '../../services/skill.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-board-skill',
  templateUrl: './board-skill.component.html',
  styleUrls: ['./board-skill.component.scss']
})
export class BoardSkillComponent {
  ENV_ICONS: string = `${environment.apiIcons}/`;
  @Input() skills:Skill[]= [];
  @Input() portfolioId?:number;
  softSkills!: Skill[];
  languages!: Skill[]; // display purpose array
  programsLanguages!: Skill[]; // display purpose array
  drivingLicenceCategories!: Skill[]; // display purpose array
  otherSkills!: Skill[];

  constructor(private skillService: SkillService){};

  ngOnChanges(changes: SimpleChanges){  
    this.skillService.getSkills(this.portfolioId) // refresh projects []
          .subscribe({
              next: (data:Skill[]) => { 
                          this.softSkills = data.filter(item=>item.categorySkillId==1) ;  
                          this.languages = data.filter(item=>item.categorySkillId==2); 
                          this.programsLanguages = data.filter(item=>item.categorySkillId==3); 
                          this.drivingLicenceCategories = data.filter(item=>item.categorySkillId==4);
                          this.otherSkills = data.filter(item=>item.categorySkillId==5); 
                        },
              error: (err:Error)=>{console.error("**error Getting Skills**");} //TODO *******
            });   
            
  }
}

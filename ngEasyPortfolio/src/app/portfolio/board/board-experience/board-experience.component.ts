import { Component, Input, SimpleChanges } from '@angular/core';
import { ExperienceService } from '../../services/experience.service';
import { Experience } from '../../component/experience/experience.interface';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-board-experience',
  templateUrl: './board-experience.component.html',
  styleUrls: ['./board-experience.component.scss']
})
export class BoardExperienceComponent {
  ENV_ICONS: string = `${environment.apiIcons}/`;
  @Input() experiences:Experience[]= [];
  @Input() portfolioId?:number;

  constructor(private experienceService: ExperienceService){};

  ngOnChanges(changes: SimpleChanges){  

    this.experienceService.getExperiences(this.portfolioId) // refresh projects []
          .subscribe({
            next: (data:Experience[]) => { this.experiences = data },
            error: (err:Error)=>{console.error("**error Getting experiences**");} //TODO *******
          });    
  }

}

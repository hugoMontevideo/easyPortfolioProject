import { Component, Input, SimpleChanges } from '@angular/core';
import { Education } from '../../component/education/education.interface';
import { EducationService } from '../../services/education.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-board-education',
  templateUrl: './board-education.component.html',
  styleUrls: ['./board-education.component.scss']
})
export class BoardEducationComponent {
  ENV_ICONS: string = `${environment.apiIcons}/`;
  @Input() educations:Education[]= [];
  @Input() portfolioId?:number;

  constructor(private educationService: EducationService){};

  ngOnChanges(changes: SimpleChanges){  

    this.educationService.getEducations(this.portfolioId) // refresh projects []
          .subscribe({
            next: (data:Education[]) => { this.educations = data },
            error: (err:Error)=>{console.error("**error Getting Skills**");} //TODO *******
          });    
  }

}

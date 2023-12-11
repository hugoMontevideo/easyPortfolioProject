import { Component, EventEmitter, Input, Output, SimpleChanges } from '@angular/core';
import { Project } from '../../component/project/project.interface';
import { ProjectService } from '../../services/project.service';

@Component({
  selector: 'app-board-project',
  templateUrl: './board-project.component.html',
  styleUrls: ['./board-project.component.scss']
})
export class BoardProjectComponent {
  @Input() projects:Project[]= [];
  @Input() portfolioId?:number;


  constructor( private projectService: ProjectService ){};

  ngOnChanges(changes: SimpleChanges){  

    this.projectService.getProjects(this.portfolioId) // refresh projects []
          .subscribe({
              next: (data:Project[]) => { this.projects = data },
              error: (err:Error)=>{console.error("**error Getting Projects**");} //TODO *******
            });    
  }

}

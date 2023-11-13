import { Component } from '@angular/core';
import { Project } from '../portfolio/model/project';
import { HttpService } from '../services/http.service';
import { ActivatedRoute } from '@angular/router';


@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.scss']
})
export class ProjectComponent {

  table: string = "project";
  projects!: Project[];

  constructor( private httpService: HttpService,
                private route: ActivatedRoute)// *** a enlever + tard ***
                {}

  getProjects(table: string, userId:number ){
    this.httpService.getAll(table, userId).subscribe({
      next: (response: Project[]) => this.projects=response,
      error: (err: Error)=> console.log("Error"),
      complete: ()=> console.log("completed")
    })



  }


}

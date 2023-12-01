import { Component, Input } from '@angular/core';
import { Project } from './project.interface';
import { ProjectService } from '../../services/project.service';

@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.scss']
})
export class ProjectComponent {

  @Input() projects!:Project[];
  @Input() portfolioId!: number;

  legend: string = "Ajouter";

  newProject: Project = {
    id: -1,
    title: "",
    description: "",
    date: new Date("1970-01-01"),
    fileName: "",
    file: null,
    portfolioId: this.portfolioId
  }
  selectedFile!: File | null;

  isProjectFormShowing: boolean = false; // display or hide form

  constructor( private projectService: ProjectService ){}

  ngOnChanges(){
    this.newProject.portfolioId = this.portfolioId;
  }

  public onAddProject = () => {
    this.legend = "Ajouter"
    this.isProjectFormShowing = true;
  }

  public onSubmitProject = ()=>{
    // // hide the form
    this.isProjectFormShowing = false; 
    this.projectService.addProject('projects' , this.newProject)
    .subscribe({
      next:(data: Project)=>{
        // Ajouter educationEdit a educations [] pour affichage
        this.projects.push(this.newProject);
      },
      error:(err:Error)=>{
        console.log("**error adding education**");
      }
    });
  }
  // if I select a file on the form :
  onSelectedFile(event:any){
    this.newProject.file = event.target.files[0];
  }

  onDeleteProject = (projectId : number, index: number):void => {
    this.projectService.deleteProject("projects" , projectId)
    .subscribe({
    next:( )=> {
      this.projects.splice(index,1)
      },
    error:(err:Error)=>{ console.log("Error while deleting education.");
        }
    }) 
  }


}

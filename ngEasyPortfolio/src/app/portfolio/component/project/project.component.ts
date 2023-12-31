import { Component, Input, SimpleChanges } from '@angular/core';
import { Project } from './project.interface';
import { ProjectService } from '../../services/project.service';
import { ProjectModel } from './project-model';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.scss']
})
export class ProjectComponent {

  @Input() projects!:ProjectModel[];
  @Input() portfolioId!: number;

  legend: string = "";
  inputError!: string;

  newProject: Project = {
    id: -1,
    title: "",
    description: "",
    date: new Date("1970-01-01"),
    fileName: "",
    documents: [],
    portfolioId: this.portfolioId
  }
  selectedFile!: File | null;
  isProjectFormShowing: boolean = false; // display or hide form

  constructor( public projectService: ProjectService ){}

  ngOnChanges(change: SimpleChanges){
    this.newProject.portfolioId = this.portfolioId;
    this.projectService.portfolioId = this.portfolioId;
  }

  public onCloseModalForm = () => {
    this.isProjectFormShowing = false;
    this.newProject = this.projectService.resetNewSkill(this.newProject.portfolioId);
  }

  public onAddProject = () => {
    this.legend = "Ajouter un projet"
    this.isProjectFormShowing = true;
    this.projectService.tempData = "add";
    this.projectService.add('projects', this.newProject)
      .subscribe({
        next:(data)=>{
          this.projects = 
            this.projectService.addProjectToArray(this.projects,  // tableau d'affichage
                                              new ProjectModel( 
                                                data.id,
                                                data.title,
                                                data.description,
                                                data.date,
                                                data.fileName,
                                                data.documents,
                                                this.newProject.portfolioId ) 
                                            );
          this.newProject.id = data.id; 
          this.newProject.title = data.title;                                  
        },
        error:(_error)=>{
          console.log("**error Adding or updating Project**");
          if(_error instanceof HttpErrorResponse ) {
            this.inputError = _error.error.title;
          } 
        }
      });
  }
 
  public onEditProject = (index: number) => {
    this.legend = "Modifier un projet"
    this.isProjectFormShowing = true;
    this.newProject = this.projectService.mapNewProject(this.projects[index]);
    this.newProject.portfolioId = this.portfolioId;
    this.projectService.tempData = "edit";  
    this.projectService.tempNumData = index;  
  }

  public onSubmitProject = ()=>{    
    // // hide the form
    this.projectService.saveProject('projects' , this.newProject, this.selectedFile!)
    .subscribe({
      next:(data)=>{
        this.isProjectFormShowing = false; 
        // Add projectModel to projects [], display purpose
        this.projects.splice(this.projectService.tempNumData,1);
        let addedProjet =  new ProjectModel( 
                                data.id,
                                data.title,
                                data.description,
                                data.date,
                                data.fileName,
                                data.documents,
                                this.newProject.portfolioId );
                                          
        this.projects.splice(this.projectService.tempNumData,0, addedProjet);
        this.newProject = this.projectService.resetNewSkill(this.newProject.portfolioId);
      },
      error:(_error)=>{
        console.log("**error Adding or updating Project**");
        if(_error instanceof HttpErrorResponse ) {
          this.inputError = _error.error.title;
        } 
      }
    });
  }
  

  // if I select a file on the form :
  onSelectedFile(event:any){
    this.selectedFile = event.target.files[0];    
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

  /**
   * Delete the picture on db
   */
  onDeletePicture = (index:number, documentId:number) => {
    this.projectService.deleteDocument("projects" , this.newProject.id, documentId)
      .subscribe({
      next:( )=> {
        this.newProject.documents.splice(index,1)
        },
      error:(err:Error)=>{ 
              // TODO  manage errors properly 
              console.log("Error while deleting document education.");
          }
    });
    // TODO
    console.log("hello", documentId);
  }


}

import { Component, EventEmitter, Input, Output, SimpleChanges } from '@angular/core';
import { Project } from './project.interface';
import { ProjectService } from '../../services/project.service';
import { HttpErrorResponse } from '@angular/common/http';
import { DocumentProject } from './document-project.interface';

@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.scss']
})
export class ProjectComponent {

  @Input() projects:Project[] =[];
  @Input() portfolioId?: number; 
  @Output() projectsChanged = new EventEmitter<Project[]>();
  legend: string = "";
  inputError?: string;
  isProjectFormShowing: boolean = false; // display or hide form

  newProject: Project = {
    id: -1,
    title: "",
    description: "",
    date: new Date("1970-01-01"),
    fileName: "",
    documents: [],
    portfolioId: this.portfolioId
  }
  selectedFile?: File ;

  constructor( public projectService: ProjectService ){}

  ngOnChanges(change: SimpleChanges){
    this.newProject.portfolioId = this.portfolioId;    
  }

  public onCloseModalForm = () => {
    this.isProjectFormShowing = false;
    this.newProject = this.projectService.resetNewProject(this.newProject.portfolioId);
  }
 
  public onEditProject = (index: number) => {
    this.legend = "Modifier un projet"
    this.isProjectFormShowing = true;
    this.newProject = this.projects[index];
    this.newProject.portfolioId = this.portfolioId;   
  }

  public onAddProject = () => {
    this.legend = "Ajouter un projet"
    this.isProjectFormShowing = true; // display form
    this.projectService.add(this.newProject)
      .subscribe({
        next:(data)=>{
          this.newProject.id = data.id; 
          this.newProject.title = data.title; 
          this.projectService.getProjects(this.newProject.portfolioId) // refresh projects[]
            .subscribe({
                next: (data:Project[]) => { 
                        this.projects = data
                        this.projectsChanged.emit(this.projects);
                       },
                error: (err:Error)=>{console.error("**error Getting Projects**");} //TODO *******
              });
        },
        error:(_error)=>{
          console.log("**error Adding Project**");
          if(_error instanceof HttpErrorResponse ) {
            this.inputError = _error.error.title;
          } 
        }
      });
  }

  public onSubmitProject = ()=>{    
    this.projectService.saveProject(this.newProject, this.selectedFile!)
    .subscribe({
      next:(data)=>{
          this.isProjectFormShowing = false;  // hide the form
          this.newProject = this.projectService.resetNewProject(this.portfolioId);
          this.projectService.getProjects(this.newProject.portfolioId) // refresh projects[]
            .subscribe({
                next: (data:Project[]) => { 
                        this.projects = data;
                        this.projectsChanged.emit(this.projects); 
                      },
                error: (_error)=>{
                    console.log("**error Getting Projects**"); 
                  }
              });
        },
      error:(_error)=>{
        console.error("**error updating Project**");
        if(_error instanceof HttpErrorResponse ) {
          this.inputError = _error.error.title;
        } 
      }
    });
  }
  // if I select a file on the form :
  onSelectedFile = (event:any):void => {
    this.selectedFile = event.target.files[0];    
  }

  onDeleteProject = (projectId : number, index: number):void => {
    this.projectService.deleteProject( projectId )
      .subscribe({
       next:( )=> {
        this.projectService.getProjects(this.newProject.portfolioId) // refresh projects[]
          .subscribe({
              next: (data:Project[]) => { 
                      this.projects = data;
                      this.projectsChanged.emit(this.projects); 
                    },
              error: (_error)=>{
                  console.log("**error Getting Projects**"); 
                }
            });
        },
       error:(err:Error)=>{ console.log("Error while deleting education.");
          }
      }) 
  }

  /**
   * Delete the picture on db
   */
  onDeletePicture = (index:number, documentId:number) => {
    this.projectService.deleteDocument( this.newProject.id, documentId )
      .subscribe({
        next:( )=> {
          this.isProjectFormShowing = true; 
          this.projectService.getDocumentProjects(this.newProject.id) // refresh documentProjects[]
            .subscribe({
                next: (data:DocumentProject[]) => { 
                  this.newProject.documents = data;
                  console.log(data);
                  
                 },
                error: (err:Error)=>{console.error(err);} //TODO *******
              }); 
          },
        error:(err:Error)=>{ 
                // TODO  manage errors properly 
                console.log("Error while deleting document education.");
            }
      });
  }


}

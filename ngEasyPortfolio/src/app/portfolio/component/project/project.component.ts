import { Component, Input } from '@angular/core';
import { Project } from './project.interface';
import { PortfolioService } from '../../services/portfolio.service';


@Component({
  selector: 'app-project',
  templateUrl: './project.component.html',
  styleUrls: ['./project.component.scss']
})
export class ProjectComponent {

  @Input() projects!:Project[];
  @Input() portfolioId!: number;

  legend: string = "Ajouter";

  currentProject: Project = {
    id: -1,
    title: "",
    description: "",
    date: new Date("1970-01-01"),
    portfolioId: this.portfolioId
  }

  isProjectFormShowing: boolean = false; // display or hide form


  constructor( private portfolioService: PortfolioService ){}

  ngOnChanges(){
    this.currentProject.portfolioId = this.portfolioId;
  }

  public onAddProject = () => {
    this.legend = "Ajouter une expérience"
    this.isProjectFormShowing = true;
  }

  public onSubmitProject = ()=>{
    // // hide the form
    this.isProjectFormShowing = false; 
    this.currentProject.portfolioId = this.portfolioId;  
    this.portfolioService.addProject('projects' , this.currentProject)
    .subscribe({
      next:(data: Project)=>{
        // Ajouter educationEdit a educations [] pour affichage
        this.projects.push(this.currentProject);
      },
      error:(err:Error)=>{
        console.log("**error adding education**");
      }
    });
  }

  onDeleteProject = (projectId : number, index: number):void => {
    this.portfolioService.deleteProject("projects" , projectId)
    .subscribe({
    next:( )=> {
      this.projects.splice(index,1)
      },
    error:(err:Error)=>{ console.log("Error while deleting education.");
        }
    }) 
  }


}

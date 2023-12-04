import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JWTTokenService } from 'src/app/services/JWTToken.service';
import { environment } from 'src/environments/environment';
import { Project } from '../component/project/project.interface';
import { Observable, catchError, throwError } from 'rxjs';
import { ProjectAddDto } from '../component/project/project-add-dto.interface';
import { ProjectModel } from '../component/project/project-model';


@Injectable()
export class ProjectService {
  ENV_DEV:string = environment.apiUrl;
  tempData: string = ""; // "add" or "edit"

  selectedFile!: File | any;

  constructor(
    private http: HttpClient,
    private jwtTokenService : JWTTokenService, 
  ) { }

  saveProject = ( table: string, newProject: Project ): Observable<any> => { 
    console.log(newProject.date);
    
    this.selectedFile = newProject.file;    
    if( this.tempData == "add") {
      // The dates are of type Date in Angular and of type LocalDate in Java 
      const formData = new FormData;
      formData.append('title', newProject.title);
      formData.append('description', newProject.description);
      formData.append('date', newProject.date.toString());
      formData.append('fileName', newProject.fileName);
      formData.append('file', this.selectedFile);
      formData.append('portfolioId', newProject.portfolioId.toString());
      return this.http.post(`${this.ENV_DEV}/${table}`, formData )
        .pipe(catchError(this.handleError)); // catch validator errors

    }
    //  TODO *******
    return this.http.put( `${this.ENV_DEV}/${table}/${newProject.id}`, newProject );
}




deleteProject = (table: string , projectId: number): Observable<any> | any => {
    return this.http.delete(`${this.ENV_DEV}/${table}/${projectId}` );  

}


  // UTILS  **************************
  public refreshSkills = (projects: ProjectModel[], project: ProjectModel) => {
    if(this.tempData == "add"){
      projects.push(project);
    }
    this.tempData = "";
    return projects;
  }

  public resetNewSkill = ( pId: number ): Project => {
    return {
              id:-1,
              title: "",
              description: "",
              date: new Date("1970-01-01"),
              fileName: "",
              file: null,
              portfolioId: pId
            };
  }

  private handleError(error: HttpErrorResponse):Observable<never>{
    return throwError(()=>error);
  }








}

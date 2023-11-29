import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JWTTokenService } from 'src/app/services/JWTToken.service';
import { environment } from 'src/environments/environment';
import { Project } from '../component/project/project.interface';
import { Observable } from 'rxjs';
import { ProjectAddDto } from '../component/project/project-add-dto.interface';


@Injectable({
  providedIn: 'root'
})
export class ProjectService {
  ENV_DEV:string = environment.apiUrl;

  selectedFile!: any;

  constructor(
    private http: HttpClient,
    private jwtTokenService : JWTTokenService, 
  ) { }
  addProject = ( table: string, newProject: Project ): Observable<any> => { 
    this.selectedFile = newProject.file;    
    
    // The dates are of type Date in Angular and of type LocalDate in Java 
    const formData = new FormData;
    formData.append('title', newProject.title);
    formData.append('description', newProject.description);
    formData.append('date', newProject.date.toString());
    formData.append('fileName', newProject.fileName);
    formData.append('file', this.selectedFile);
    formData.append('portfolioId', newProject.portfolioId.toString());

    return this.http.post(`${this.ENV_DEV}/${table}`, formData );
}

deleteProject = (table: string , projectId: number): Observable<any> | any => {
    return this.http.delete(`${this.ENV_DEV}/${table}/${projectId}` );  

}





}

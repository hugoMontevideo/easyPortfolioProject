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


  constructor(
    private http: HttpClient,
    private jwtTokenService : JWTTokenService, 
  ) { }

  addProject = ( table: string, currentProject: Project ): Observable<any> => {     
    // The dates are of type Date in Angular and of type LocalDate in Java 
    let projectAdd: ProjectAddDto = {
                        title: currentProject.title,
                        description: currentProject.description,
                        date: currentProject.date,
                        portfolioId: currentProject.portfolioId
                    }
    return this.http.post(`${this.ENV_DEV}/${table}`, projectAdd );
}

deleteProject = (table: string , projectId: number): Observable<any> | any => {
    return this.http.delete(`${this.ENV_DEV}/${table}/${projectId}` );  

}





}

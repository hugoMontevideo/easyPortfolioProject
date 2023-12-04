import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JWTTokenService } from 'src/app/services/JWTToken.service';
import { environment } from 'src/environments/environment';
import { Experience } from '../component/experience/experience.interface';
import { Observable, catchError, throwError } from 'rxjs';
import { ExperienceAddDto } from '../component/experience/experience-add-dto.interface';
import { ExperienceModel } from '../component/experience/experience-model';


@Injectable()

export class ExperienceService {
  ENV_DEV:string = environment.apiUrl;
  tempData: string = ""; // "add" or "edit"

  constructor(
    private http: HttpClient,
    private jwtTokenService : JWTTokenService
  ) { }

  //  add or update experience  
  saveExperience = ( table: string, newExperience: Experience ): Observable<any> => {
     if( newExperience.id == -1 ){
      let experience : ExperienceAddDto = {
              title: newExperience.title,
              company: newExperience.company,
              description: newExperience.description,
              startDate: newExperience.startDate,
              endDate:newExperience.endDate,
              portfolioId:newExperience.portfolioId
            };            
      return this.http.post(`${this.ENV_DEV}/${table}`, experience )
        .pipe(catchError(this.handleError)); // catch validator api errors
    }
    return this.http.put( `${this.ENV_DEV}/${table}/${newExperience.id}`, newExperience );
  }

  deleteExperience = (table: string , experienceId: number): Observable<any> | any => {
      // this.jwtTokenService.setToken(this.jwtTokenService.getToken());
      // if(this.jwtTokenService.isLogged()){
          return this.http.delete(`${this.ENV_DEV}/${table}/${experienceId}`);  
      // }
  }


 // UTILS ****************************
 dateToLong = (date : string ): number => {
  let tempDate: Date = new Date(date);
  return tempDate.getTime();
}
  public refreshExperiences = (experiences: ExperienceModel[], experience: ExperienceModel) => {
    if(this.tempData == "add"){
      experiences.push(experience);
    }
    this.tempData = "";
    return experiences;
  }

  public resetNewExperience = ( pId: number ): Experience => {
     return { id: -1,
              title: "",
              company:"",
              description: "",
              startDate: new Date("1970-01-01"),
              endDate: new Date("1970-01-01"),
              portfolioId: pId
            };
  }
  
  private handleError(error: HttpErrorResponse):Observable<never>{    
    return throwError(()=>error);
  }


}

import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JWTTokenService } from 'src/app/services/JWTToken.service';
import { environment } from 'src/environments/environment';
import { Experience } from '../component/experience/experience.interface';
import { Observable, catchError, throwError } from 'rxjs';
import { ExperienceAddDto } from '../component/experience/experience-add-dto.interface';



@Injectable()

export class ExperienceService {
  ENV_DEV:string = environment.apiUrl;

  constructor(
    private http: HttpClient,
    private jwtTokenService : JWTTokenService
  ) { }
  getExperiences = ( portfolioId:number | any ):Observable<Experience[]> => {
    return this.http.get<Experience[]>( `${this.ENV_DEV}/portfolios/${portfolioId}/experiences`);
  }

  //  add or update experience  
  saveExperience = ( newExperience: Experience ): Observable<any> => {
    return this.http.put( `${this.ENV_DEV}/experiences/${newExperience.id}`, newExperience )
    .pipe(catchError(this.handleError)); // catch validator errors
  }

  add = ( newExperience: Experience ): Observable<any> => { 
      let experienceAdd : ExperienceAddDto = {
        title: "nouveau diplôme en cours",
        portfolioId: newExperience.portfolioId
    }
    return this.http.post(`${this.ENV_DEV}/experiences`, experienceAdd )
      .pipe(catchError(this.handleError)); // catch validator error
  }

  deleteExperience = ( experienceId: number): Observable<any> | any => {
      // this.jwtTokenService.setToken(this.jwtTokenService.getToken());
      // if(this.jwtTokenService.isLogged()){
          return this.http.delete(`${this.ENV_DEV}/experiences/${experienceId}`);  
      // }
  }


 // UTILS ****************************
  dateToLong = (date : string ): number => {
    let tempDate: Date = new Date(date);
    return tempDate.getTime();
  }
  

  public resetNewExperience = ( portfolioId: number | any ): Experience => {
     return { id: -1,
              title: "",
              company:"",
              description: "",
              startDate: new Date("1970-01-01"),
              endDate: new Date("1970-01-01"),
              portfolioId: portfolioId
            };
  }
  
  private handleError(error: HttpErrorResponse):Observable<never>{    
    return throwError(()=>error);
  }


}

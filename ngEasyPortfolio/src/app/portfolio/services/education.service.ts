import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JWTTokenService } from 'src/app/services/JWTToken.service';
import { environment } from 'src/environments/environment';
import { Education } from '../component/education/education.interface';
import { Observable, catchError, throwError } from 'rxjs';
import { EducationAddDto } from '../component/education/education-add-dto.interface';

@Injectable()

export class EducationService {
  ENV_DEV:string = environment.apiUrl;

  constructor(
    private http: HttpClient,
    private jwtTokenService : JWTTokenService, 
  ) { }

  getEducations = ( portfolioId:number | any ):Observable<Education[]> => {
    return this.http.get<Education[]>( `${this.ENV_DEV}/portfolios/${portfolioId}/educations`);
  }

  saveEducation = ( newEducation: Education ): Observable<any> => {             
    return this.http.put(`${this.ENV_DEV}/educations/${newEducation.id}`, newEducation )
        .pipe(catchError(this.handleError)); // catch validator errors
  }

  add = ( newEducation: Education ): Observable<any> => { 
      let educationAdd : EducationAddDto = {
        training: "nouveau diplôme en cours",
        portfolioId: newEducation.portfolioId
    }
    return this.http.post(`${this.ENV_DEV}/educations`, educationAdd )
      .pipe(catchError(this.handleError)); // catch validator error
  }
  

  deleteEducation = ( educationId: number): Observable<any> | any => {
      // this.jwtTokenService.setToken(this.jwtTokenService.getToken());
      return this.http.delete( `${this.ENV_DEV}/educations/${educationId}` );  
  }

  // UTILS  **************************
  public resetNewEducation = ( portfolioId: number|any ): Education => {
    return {
              id: -1,
              training: "",
              school: "",
              degree: "",
              startDate: new Date("1970-01-01"),
              endDate: new Date("1970-01-01"),
              description: "",
              portfolioId: portfolioId
            };
  }

  private handleError(error: HttpErrorResponse):Observable<never>{
    return throwError(()=>error);
  }








}

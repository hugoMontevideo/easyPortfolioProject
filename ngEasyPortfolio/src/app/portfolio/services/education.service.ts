import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JWTTokenService } from 'src/app/services/JWTToken.service';
import { environment } from 'src/environments/environment';
import { Education } from '../component/education/education.interface';
import { Observable, catchError, throwError } from 'rxjs';
import { EducationAddDto } from '../component/education/education-add-dto.interface';
import { EducationModel } from '../component/education/education-model';

@Injectable()

export class EducationService {
  ENV_DEV:string = environment.apiUrl;
  tempData: string = ""; // "add" or "edit"

  constructor(
    private http: HttpClient,
    private jwtTokenService : JWTTokenService, 
  ) { }

  saveEducation = ( table: string, newEducation: Education ): Observable<any> => {     
    // The dates are of type Date in Angular and of type LocalDate in Java 
    if(this.tempData == "add") {
      let education: EducationAddDto = {
                          training: newEducation.training,
                          school: newEducation.school,
                          degree: newEducation.degree,
                          startDate: newEducation.startDate,
                          endDate: newEducation.endDate,
                          description: newEducation.description,
                          portfolioId: newEducation.portfolioId
                      }
      return this.http.post(`${this.ENV_DEV}/${table}`, education )
        .pipe(catchError(this.handleError)); // catch validator errors

    }
    return this.http.post(`${this.ENV_DEV}/${table}/${newEducation.id}`, newEducation );

}

deleteEducation = (table: string , educationId: number): Observable<any> | any => {
    // this.jwtTokenService.setToken(this.jwtTokenService.getToken());
    return this.http.delete( `${this.ENV_DEV}/${table}/${educationId}` );  
}

  // UTILS  **************************
  public refreshSkills = (educations: EducationModel[], education: EducationModel) => {
    if(this.tempData == "add"){
      educations.push(education);
    }
    this.tempData = "";
    return educations;
  }

  public resetNewEducation = ( pId: number ): Education => {
    return {
              id: -1,
              training: "",
              school: "",
              degree: "",
              startDate: new Date("1970-01-01"),
              endDate: new Date,
              description: "",
              portfolioId: pId
            };
  }

  private handleError(error: HttpErrorResponse):Observable<never>{
    return throwError(()=>error);
  }








}

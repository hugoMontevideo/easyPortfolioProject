import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JWTTokenService } from 'src/app/services/JWTToken.service';
import { environment } from 'src/environments/environment';
import { Education } from '../component/education/education.interface';
import { Observable } from 'rxjs';
import { EducationAddDto } from '../component/education/education-add-dto.interface';

@Injectable()

export class EducationService {
  ENV_DEV:string = environment.apiUrl;

  constructor(
    private http: HttpClient,
    private jwtTokenService : JWTTokenService, 
  ) { }

  addEducation = ( table: string, newEducation: Education ): Observable<any> => {     
    // The dates are of type Date in Angular and of type LocalDate in Java 
    let educationAdd: EducationAddDto = {
                        training: newEducation.training,
                        school: newEducation.school,
                        degree: newEducation.degree,
                        startDate: newEducation.startDate,
                        endDate: newEducation.endDate,
                        description: newEducation.description,
                        portfolioId: newEducation.portfolioId
                    }
    return this.http.post(`${this.ENV_DEV}/${table}`, educationAdd );
}

deleteEducation = (table: string , educationId: number): Observable<any> | any => {
    // this.jwtTokenService.setToken(this.jwtTokenService.getToken());
    return this.http.delete( `${this.ENV_DEV}/${table}/${educationId}` );  
}








}

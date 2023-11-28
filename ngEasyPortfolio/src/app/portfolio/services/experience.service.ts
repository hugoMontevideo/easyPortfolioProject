import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JWTTokenService } from 'src/app/services/JWTToken.service';
import { environment } from 'src/environments/environment';
import { Experience } from '../component/experience/experience.interface';
import { Observable } from 'rxjs';
import { ExperienceAddDto } from '../component/experience/experience-add-dto.interface';


@Injectable()

export class ExperienceService {
  ENV_DEV:string = environment.apiUrl;

  constructor(
    private http: HttpClient,
    private jwtTokenService : JWTTokenService, 
  ) { }

  addExperience = ( table: string, newExperience: Experience ): Observable<any> => {    
        
      let startDate: number = this.dateToLong(newExperience.startDate);
      let endDate: number = this.dateToLong(newExperience.endDate);
      let experience : ExperienceAddDto = {
              title: newExperience.title,
              company: newExperience.company,
              description: newExperience.description,
              startDate: startDate,
              endDate:endDate,
              portfolioId:newExperience.portfolioId
          }
      return this.http.post(`${this.ENV_DEV}/${table}`, experience ,{responseType: "json"} );
  }

  deleteExperience = (table: string , experienceId: number): Observable<any> | any => {
      // this.jwtTokenService.setToken(this.jwtTokenService.getToken());
      // if(this.jwtTokenService.isLogged()){
          return this.http.delete(`${this.ENV_DEV}/${table}/${experienceId}`,{responseType: "json"} );  
      // }
  }


 // UTILS ****************************
 dateToLong = (date : string ): number => {
  let tempDate: Date = new Date(date);
  return tempDate.getTime();
}


}

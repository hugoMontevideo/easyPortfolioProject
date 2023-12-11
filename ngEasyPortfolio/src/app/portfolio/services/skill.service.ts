import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JWTTokenService } from 'src/app/services/JWTToken.service';
import { Skill } from '../component/skill/skill.interface';
import { Observable, catchError, throwError } from 'rxjs';
import { SkillAddDto } from '../component/skill/skill-add-dto.interface';
import { environment } from 'src/environments/environment';

@Injectable()

export class SkillService {
  ENV_DEV:string = environment.apiUrl;
  tempData: string = ""; // "add" or "edit"

  constructor(
    private http: HttpClient,
    private jwtTokenService : JWTTokenService
  ) { };

  getSkills = ( portfolioId:number | any ):Observable<Skill[]> => {
    return this.http.get<Skill[]>( `${this.ENV_DEV}/portfolios/${portfolioId}/skills`);
  }

  add = ( newSkill: Skill ): Observable<any> => { 
      let skill : SkillAddDto = {
        title: "nouvelle compétence en cours",
        portfolioId: newSkill.portfolioId
    }
    return this.http.post(`${this.ENV_DEV}/skills`, skill )
      .pipe(catchError(this.handleError)); // catch validator error
  }

  saveSkill = ( newSkill: Skill ): Observable<any> => {               
    return this.http.put( `${this.ENV_DEV}/skills/${newSkill.id}`, newSkill )
      .pipe(catchError(this.handleError)); // catch validator errors
  }

  deleteSkill= (skillId: number): Observable<any> | any => {
      if(this.jwtTokenService.isLogged()){
          return this.http.delete(`${this.ENV_DEV}/skills/${skillId}`);  
      }
  }

  // UTILS  **************************

  public resetNewSkill = ( portfolioId: number ): Skill => {
    return {
              id:-1,
              title: "",
              description: "",
              portfolioId: portfolioId
            };
  }

  private handleError(error: HttpErrorResponse):Observable<never>{
    return throwError(()=>error);
  }




}

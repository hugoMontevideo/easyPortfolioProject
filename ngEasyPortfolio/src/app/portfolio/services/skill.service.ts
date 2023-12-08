import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JWTTokenService } from 'src/app/services/JWTToken.service';
import { Skill } from '../component/skill/skill.interface';
import { Observable, catchError, throwError } from 'rxjs';
import { SkillAddDto } from '../component/skill/skill-add-dto.interface';
import { environment } from 'src/environments/environment';
import { SkillModel } from '../component/skill/skill-model';

@Injectable()
export class SkillService {
  ENV_DEV:string = environment.apiUrl;
  tempData: string = ""; // "add" or "edit"

  constructor(
    private http: HttpClient,
    private jwtTokenService : JWTTokenService
  ) { }

  saveSkill = ( table: string, newSkill: Skill ): Observable<any> => {       
     if( newSkill.id == -1 ){
       let skill : SkillAddDto = {
           title: newSkill.title,
           description: newSkill.description,
           portfolioId: newSkill.portfolioId
       };       
       return this.http.post( `${this.ENV_DEV}/${table}`, skill )
        .pipe(catchError(this.handleError)); // catch validator errors
     } else {  
      return this.http.put( `${this.ENV_DEV}/${table}/${newSkill.id}`, newSkill );
     }  
  }

  deleteSkill= (table: string , skillId: number): Observable<any> | any => {
      if(this.jwtTokenService.isLogged()){
          return this.http.delete(`${this.ENV_DEV}/${table}/${skillId}`);  
      }
  }

  editSkill = (table: string, editSkill: Skill): Observable<any> | any => {
    let skill : SkillAddDto = {
      title: editSkill.title,
      description: editSkill.description,
      portfolioId: editSkill.portfolioId,
    }       
    return this.http.post(`${this.ENV_DEV}/${table}`, skill );
  }


  // UTILS  **************************
  public refreshSkills = (skills: SkillModel[], skill: SkillModel) => {
    if(this.tempData == "add"){
      skills.push(skill);
    }
    this.tempData = "";
    return skills;
  }

  public resetNewSkill = ( pId: number ): Skill => {
    return {
              id:-1,
              title: "",
              description: "",
              portfolioId: pId
            };
  }

  private handleError(error: HttpErrorResponse):Observable<never>{
    return throwError(()=>error);
  }




}

import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JWTTokenService } from 'src/app/services/JWTToken.service';
import { Skill } from '../component/skill/skill.interface';
import { Observable } from 'rxjs';
import { SkillAddDto } from '../component/skill/skill-add-dto.interface';
import { environment } from 'src/environments/environment';
import { SkillModel } from '../component/skill/skill-model';

@Injectable()
export class SkillService {
  ENV_DEV:string = environment.apiUrl;

  constructor(
    private http: HttpClient,
    private jwtTokenService : JWTTokenService, 
  ) { }

  addSkill = ( table: string, newSkill: Skill ): Observable<any> => {     
        
      let skill : SkillAddDto = {
          title: newSkill.title,
          description: newSkill.description,
          portfolioId: newSkill.portfolioId
      }       
      return this.http.post(`${this.ENV_DEV}/${table}`, skill );
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

  public resetNewSkill = ( pId: number ): Skill => {
    let newSkill: Skill = {
      id:-1,
      title: "",
      description: "",
      portfolioId: pId
    };
    return newSkill;
  }

  public getNewSkillModel = (skill: Skill): SkillModel => {
    return new SkillModel (
          skill.id,
          skill.title,
          skill.description,
          skill.portfolioId
        );
      // return skillModel;
  }



}

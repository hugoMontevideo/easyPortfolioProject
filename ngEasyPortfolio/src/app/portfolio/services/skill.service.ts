import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JWTTokenService } from 'src/app/services/JWTToken.service';
import { Skill } from '../component/skill/skill.interface';
import { Observable } from 'rxjs';
import { SkillAddDto } from '../component/skill/skill-add-dto.interface';
import { environment } from 'src/environments/environment';

@Injectable()
export class SkillService {
  ENV_DEV:string = environment.apiUrl;

  constructor(
    private http: HttpClient,
    private jwtTokenService : JWTTokenService, 
  ) { }

  addSkill = ( table: string, currentSkill: Skill ): Observable<any> => {     
        
      let skill : SkillAddDto = {
          title: currentSkill.title,
          description: currentSkill.description,
          portfolioId: currentSkill.portfolioId,
      }       
      return this.http.post(`${this.ENV_DEV}/${table}`, skill );
  }

  deleteSkill= (table: string , skillId: number): Observable<any> | any => {
      if(this.jwtTokenService.isLogged()){
          return this.http.delete(`${this.ENV_DEV}/${table}/${skillId}`);  
      }
  }


}

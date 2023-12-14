import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { HttpClient } from "@angular/common/http";
import { environment } from "src/environments/environment";
import { LoginUser } from "src/app/login/login-user.interface";
import { Portfolio } from "../model/portfolio/portfolio.interface";
import { JWTTokenService } from "src/app/services/JWTToken.service";
import { Skill } from "../component/skill/skill.interface";
import { Experience } from "../component/experience/experience.interface";
import { Education } from "../component/education/education.interface";
import { EducationAddDto } from "../component/education/education-add-dto.interface";
import { ExperienceAddDto } from "../component/experience/experience-add-dto.interface";
import { SkillAddDto } from "../component/skill/skill-add-dto.interface";
import { ProjectAddDto } from "../component/project/project-add-dto.interface";
import { Project } from "../component/project/project.interface";

@Injectable()

export class PortfolioService {
    ENV_DEV:string = environment.apiUrl;
    // loginUser: LoginUser = {
    //     email: '',
    //     token: '',
    //   } ;


    constructor ( 
                private http: HttpClient,
                private jwtTokenService : JWTTokenService, 
         ) {};

    // get by id
    public getPortfolioById(table:string, id:number): Observable<Portfolio> | any {
        // get the token
        this.jwtTokenService.setToken(this.jwtTokenService.getToken());
        if(this.jwtTokenService.isLogged()){

            return this.http.get<Portfolio>(`${this.ENV_DEV}/${table}/${id}`);
        }
    }

    // get All
    getAllPortfolios( table: string): Observable<Portfolio[]> {
        return this.http.get<Portfolio[]>(`${this.ENV_DEV}/${table}`);
    }



    /** UTILS */

    getId = (id : string | any ): number => {
        return parseInt(id) ?? 0;
    }

    // table : PROJECT *************
    


    // table : EDUCATION *************
    


    // table : EXPERIENCE *************

  

    // table : SKILL *************

  



   

    
}



// return this.http.get(`http://localhost/angular/ngEasyPortfolio/src/app/services/api/${table}.php?action=readById&id=${userId}&p_id=${portfolioId}`, {headers:headers, responseType: "json"});
// .pipe(map(data => {
//     if(user){
//       console.log(user);
      
//     //   sessionStorage.setItem('currentUser', JSON.stringify(user)); 
      
//     }
//     return data ;
//   }));
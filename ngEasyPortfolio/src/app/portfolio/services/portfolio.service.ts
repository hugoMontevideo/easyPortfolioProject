import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { HttpClient } from "@angular/common/http";
import { environment } from "src/environments/environment";

import { LoginUser } from "src/app/login/login-user.interface";
import { Portfolio } from "../model/portfolio/portfolio.interface";
import { JWTTokenService } from "src/app/services/JWTToken.service";
import { Skill } from "../component/skill/skill.interface";
import { Experience } from "../component/experience/experience.interface";
import { ExperienceDto } from "../component/experience/experience-dto.interface";
import { Education } from "../component/education/education.interface";

@Injectable()

export class PortfolioService {
    ENV_DEV:string = environment.apiUrl;
    loginUser: LoginUser = {
        email: '',
        token: '',
      } ;


    constructor ( 
                private http: HttpClient,
                private jwtTokenService : JWTTokenService
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
    getAllPortfolios( table: string): Observable<any> {
        return this.http.get<any>(`${this.ENV_DEV}/${table}`, { responseType: "json"});
    }


    verifyToken = () => {

    }

    getId = (id : string | any ): number => {
        return parseInt(id) ?? 0;
      
    }
    // table : EDUCATION *************
    addEducation = ( table: string, educationEdit: Education ): Observable<any> => {  
        console.log(educationEdit.startDate);
        console.log(educationEdit.endDate);
        
          
        // The dates are of type Date in Angular and of type LocalDate in Java 
        return this.http.post(`${this.ENV_DEV}/${table}`, educationEdit ,{responseType: "json"} );
    }


    // table : EXPERIENCE *************

    addExperience = ( table: string, experienceEdit: Experience ): Observable<any> => {    
        
        let startDate: number = this.dateToLong(experienceEdit.startDate);
        let endDate: number = this.dateToLong(experienceEdit.endDate);
        let experience : ExperienceDto = {
                id: 0,
                title: experienceEdit.title,
                company: experienceEdit.company,
                description: experienceEdit.description,
                startDate: startDate,
                endDate:endDate,
                portfolioId:experienceEdit.portfolioId
            }
        return this.http.post(`${this.ENV_DEV}/${table}`, experience ,{responseType: "json"} );
    }

    deleteExperience = (table: string , experienceId: number): Observable<any> | any => {
        // this.jwtTokenService.setToken(this.jwtTokenService.getToken());
        // if(this.jwtTokenService.isLogged()){
            return this.http.delete(`${this.ENV_DEV}/${table}/${experienceId}`,{responseType: "json"} );  
        // }
    }

    // table : SKILL *************

    addSkill = ( table: string, skillEdit: Skill ): Observable<any> => {        
        return this.http.post(`${this.ENV_DEV}/${table}`, skillEdit ,{responseType: "json"} );
    }

    deleteSkill= (table: string , skillId: number): Observable<any> | any => {
        // this.jwtTokenService.setToken(this.jwtTokenService.getToken());
        if(this.jwtTokenService.isLogged()){
            return this.http.delete(`${this.ENV_DEV}/${table}/${skillId}`,{responseType: "json"} );  
        }
    }




    // UTILS ****************************
    dateToLong = (date : string ): number => {
        let tempDate: Date = new Date(date);
        return tempDate.getTime();
    }

    
}



// return this.http.get(`http://localhost/angular/ngEasyPortfolio/src/app/services/api/${table}.php?action=readById&id=${userId}&p_id=${portfolioId}`, {headers:headers, responseType: "json"});
// .pipe(map(data => {
//     if(user){
//       console.log(user);
      
//     //   sessionStorage.setItem('currentUser', JSON.stringify(user)); 
      
//     }
//     return data ;
//   }));
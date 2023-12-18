import { Injectable } from "@angular/core";
import { Observable, catchError, throwError } from "rxjs";
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { environment } from "src/environments/environment";
import { Portfolio } from "../model/portfolio/portfolio.interface";
import { PortfolioDTO } from "../model/portfolio/portfolioDTO.interface";
import { JWTTokenService } from "src/app/services/JWTToken.service";

@Injectable()

export class PortfolioService {
    ENV_DEV:string = environment.apiUrl;

    constructor ( 
                private http: HttpClient,
                private jwtTokenService : JWTTokenService, 
         ) {};
    
    /** update portfolio */    
    savePortfolio = ( portfolio: Portfolio ): Observable<Portfolio> => { 

        const savedPortfolio :PortfolioDTO = {
                                id: portfolio.id,
                                title: portfolio.title,
                                description: portfolio.description,
                                name: portfolio.name,
                                firstname: portfolio.firstname,
                                email:portfolio.email,
                                city: portfolio.city,
                                profileImgPath:portfolio.profileImgPath,
                                aboutMe: portfolio.aboutMe,
                                userId: portfolio.user?.id
                            }
        return this.http.put<Portfolio>(`${this.ENV_DEV}/portfolios/${portfolio.id}`, savedPortfolio )
            .pipe(catchError(this.handleError)); // catch validator errors
    }

    /** getbyid portfolio */ 
    public getPortfolioById(id:number): Observable<Portfolio> | any {
        // get the token
        this.jwtTokenService.setToken(this.jwtTokenService.getToken());
        if(this.jwtTokenService.isLogged()){
            return this.http.get<Portfolio>(`${this.ENV_DEV}/portfolios/${id}`);
        }
    }

    // get All
    getAllPortfolios( table: string): Observable<Portfolio[]> {
        return this.http.get<Portfolio[]>(`${this.ENV_DEV}/${table}`);
    }
    
    // save picture AboutMe
    savePicture = ( portfolioId:number, selectedFile: File ): Observable<any> => { 
        let formData = new FormData;
        formData.append('file', selectedFile as File);
        return this.http.put(`${this.ENV_DEV}/portfolios/${portfolioId}/about_me_picture`, formData)
            .pipe(catchError(this.handleError)); // catch validator errors
    }


    /** UTILS */
    private handleError(error: HttpErrorResponse):Observable<never>{
        return throwError(()=>error);
    }


    getId = (id : string | any ): number => {
        return parseInt(id) ?? 0;
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
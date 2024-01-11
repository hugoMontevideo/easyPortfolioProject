import { Injectable } from "@angular/core";
import { Observable, catchError, throwError } from "rxjs";
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { environment } from "src/environments/environment";
import { Portfolio } from "../model/portfolio/portfolio.interface";
import { PortfolioDTO } from "../model/portfolio/portfolioDTO.interface";
import { JWTTokenService } from "src/app/services/JWTToken.service";
import { User } from "src/app/core/user/user.interface";
import { PortfolioAddDto } from "../model/portfolio/portolio-add-dto.interface";
import { BoardManager } from "../component/portfolio-list-item/board-manager.interface";

@Injectable()

export class PortfolioService {
    ENV_BASE :string = environment.baseUrl;
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
    public getPortfolioById(id:number | any): Observable<Portfolio> | any {
        // get the token
        this.jwtTokenService.setToken(this.jwtTokenService.getToken());
        if(this.jwtTokenService.isLogged()){
            return this.http.get<Portfolio>(`${this.ENV_DEV}/portfolios/${id}`);
        }
    }

    
  public add = ( newPortfolio: Portfolio ): Observable<Portfolio> => { 
    let definedId: number = (newPortfolio.user !== undefined) ? newPortfolio.user.id : 0;  
    let portfolioAdd : PortfolioAddDto = {
      title: "nouveau portfolio en cours",
      userId: definedId
    }
      return this.http.post<Portfolio>(`${this.ENV_DEV}/portfolios`, portfolioAdd )
        .pipe(catchError(this.handleError)); // catch validator error
  }

    // get All
    getAllPortfolios(userEmail: string): Observable<Portfolio[]> | any {            
            return this.http.get<Portfolio[]>(`${this.ENV_BASE}/auth/users/${userEmail}/portfolios`);
        

    }
    
    // save picture AboutMe
    savePicture = ( portfolioId:number, selectedFile: File ): Observable<any> => { 
        let formData = new FormData;
        formData.append('file', selectedFile as File);
        return this.http.put(`${this.ENV_DEV}/portfolios/${portfolioId}/about_me_picture`, formData)
            .pipe(catchError(this.handleError)); // catch validator errors
    }

    deletePortfolio = ( portfolioId: number): Observable<any> | any => {
        return this.http.delete(`${this.ENV_DEV}/portfolios/${portfolioId}` );  
    }

    getUserByEmail = (): Observable<any> | any => {    
      if(  this.jwtTokenService.getUser() != null) {
          let email = this.jwtTokenService.getUser();
          return this.http.get<any>(`http://localhost/auth/users/${email}`);
      }
  }


    /** UTILS */

    detectDevice = () => {
      return window.innerWidth < 1050; 
    }

    private handleError(error: HttpErrorResponse):Observable<never>{
        return throwError(()=>error);
    }

    getId = (id : string | any ): number => {
        return parseInt(id) ?? 0;
    }

  public resetNewPortfolio = ( user: User|any): Portfolio => {
    let definedId: number = (user !== undefined) ? user.id : 0;
    return {
            id: -1,
            title: "",
            description: "",
            name: "",
            firstname: "",
            email:"",
            city: "",
            profileImgPath: "",
            aboutMe: "",
            projects: [],
            educations:[],
            experiences:[],
            skills: [],
            user: {
              id: definedId,
              name: "",
              firstname: "",
              email: "",
              password: "",
              dateInscription: new Date("1970-01-01"),
              dateConnect: new Date("1970-01-01"),
              profileImgPath: "",
              roles: []
            }
          };
  }

  /**
   * Manages forms and board displaying
   * @returns BoardManager
   */
  resetBoardManager=(): BoardManager =>{
    const boardManager: BoardManager = {
      isShowingHome:false,
      isShowingProjects : false,
      isShowingSkills : false,
      isShowingEducs : false,
      isShowingExpers : false,
      isPortfolioFormShowing : false
    }
    return boardManager;
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
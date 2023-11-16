import { Injectable } from "@angular/core";
import { Observable, map } from "rxjs";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { environment } from "src/environments/environment";
import { PortfolioDTO } from "../model/portfolio/portfolioDTO.interface";
import { LoginUser } from "src/app/login/login-user.interface";

@Injectable()

export class PortfolioService {
    ENV_DEV:string = environment.apiUrl;
    loginUser: LoginUser = {
        id: 0,
        email: '',
        token: ''
      } ;

    constructor ( private http: HttpClient ) {}


    // get by id
    public getPortfolioById(table:string, id:number): Observable<PortfolioDTO>{
        return this.http.get<PortfolioDTO>(`${this.ENV_DEV}/${table}/${id}`, {responseType: "json"});
    }

    

    getData( table: string, id: number ): Observable<any> {
        // let currentUser = {token: ""};
        // let headers = new HttpHeaders();
        // headers = headers.set("Authorization", "Bearer ");
            
        return this.http.get<any>(`${this.ENV_DEV}/${table}`, {responseType: "json"} );
    }

    getAll( table: string): Observable<any> {
        // let currentUser = {token: ""};
        let headers = new HttpHeaders();
        headers = headers.set("Authorization", "Bearer ");
        let anything: any = sessionStorage.getItem("currentUser");
        
        if( anything != null){
            this.loginUser = JSON.parse(anything);
            headers = headers.set("Authorization", "Bearer" + this.loginUser.token);
            console.log(this.loginUser.token);
          
        }
            
        return this.http.get<any>(`${this.ENV_DEV}/${table}`, {headers:headers, responseType: "json"});
        // .pipe(map(data => {
        //     if(user){
        //       console.log(user);
              
        //     //   sessionStorage.setItem('currentUser', JSON.stringify(user)); 
              
        //     }
        //     return data ;
        //   }));
    }

    
    getById( table: string, userId: number, portfolioId: number ): Observable<any> {
        let currentUser = {token: ""};
        let headers = new HttpHeaders();
        headers = headers.set("Authorization", "Bearer ");
        let anything: any = sessionStorage.getItem("currentUser");

        
        
        if( anything != null){
            currentUser = JSON.parse(anything);
            headers = headers.set("Authorization", "Bearer" + currentUser.token);
            console.log(currentUser.token);
          
        }
            
        return this.http.get(`http://localhost/angular/ngEasyPortfolio/src/app/services/api/${table}.php?action=readById&id=${userId}&p_id=${portfolioId}`, {headers:headers, responseType: "json"});
        // .pipe(map(data => {
        //     if(user){
        //       console.log(user);
              
        //     //   sessionStorage.setItem('currentUser', JSON.stringify(user)); 
              
        //     }
        //     return data ;
        //   }));
    }






    
}
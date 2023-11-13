import { Injectable } from "@angular/core";
import { Observable, map } from "rxjs";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { environment } from "src/environments/environment";
import { PortfolioDTO } from "../model/portfolio/portfolioDTO.interface";

@Injectable()

export class PortfolioService {
    ENV_DEV:string = environment.apiUrl;

    constructor ( private http: HttpClient ) {}


    // get by id
    public getPortfolioById(table:string, id:number): Observable<PortfolioDTO>{
        return this.http.get<PortfolioDTO>(`${this.ENV_DEV}/${table}/${id}`, {responseType: "json"});
    }

    

    getData( table: string, id: number ): Observable<any> {
        // let currentUser = {token: ""};
        // let headers = new HttpHeaders();
        // headers = headers.set("Authorization", "Bearer ");
            
        return this.http.get(`http://localhost/angular/ngEasyPortfolio/src/app/services/api/token/login.php?action=read&id=${id}`, {responseType: "json"} );
    }

    getAll( table: string, userId: number ): Observable<any> {
        let currentUser = {token: ""};
        let headers = new HttpHeaders();
        headers = headers.set("Authorization", "Bearer ");
        let anything: any = sessionStorage.getItem("currentUser");

        
        
        if( anything != null){
            currentUser = JSON.parse(anything);
            headers = headers.set("Authorization", "Bearer" + currentUser.token);
            console.log(currentUser.token);
          
        }
            
        return this.http.get(`http://localhost/angular/ngEasyPortfolio/src/app/services/api/${table}.php?action=readAll&id=${userId}`, {headers:headers, responseType: "json"});
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
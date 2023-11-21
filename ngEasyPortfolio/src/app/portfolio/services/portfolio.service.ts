import { Injectable } from "@angular/core";
import { Observable, map } from "rxjs";
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { environment } from "src/environments/environment";

import { LoginUser } from "src/app/login/login-user.interface";
import { Portfolio } from "../model/portfolio/portfolio.interface";

@Injectable()

export class PortfolioService {
    ENV_DEV:string = environment.apiUrl;
    loginUser: LoginUser = {
        login: '',
        token: '',
        conButton: 'Connexion'
      } ;

    constructor ( private http: HttpClient ) {};

    // get by id
    public getPortfolioById(table:string, id:number): Observable<Portfolio>{
        // let headers = new HttpHeaders();
        // let storage: any = sessionStorage.getItem("currentUser");
        // if( storage != null){
        //     this.loginUser = JSON.parse(storage);
        //     headers = headers.set("Authorization", "Bearer " + this.loginUser.token);   
        //     console.log("headers ok");  
        // }
        return this.http.get<Portfolio>(`${this.ENV_DEV}/${table}/${id}`);
    }


    getAll( table: string): Observable<any> {
        return this.http.get<any>(`${this.ENV_DEV}/${table}`, { responseType: "json"});
    }

    getData( table: string, id: number ): Observable<any> {
        // let currentUser = {token: ""};
        // let headers = new HttpHeaders();
        // headers = headers.set("Authorization", "Bearer ");
            
        return this.http.get<any>(`${this.ENV_DEV}/${table}`, {responseType: "json"} );
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
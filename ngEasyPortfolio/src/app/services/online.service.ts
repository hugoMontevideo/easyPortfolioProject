import { HttpBackend, HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { environment } from "src/environments/environment";
import { Portfolio } from "../portfolio/model/portfolio/portfolio.interface";
import { Observable } from "rxjs";


@Injectable({
    providedIn: 'root'
  })
  export class OnlineService {
    ENV_BASE :string = environment.baseUrl;
    // ENV_DEV : string = environment.apiUrl;

    httpClient!: HttpClient; // de cette façon on évite l'interceptor (middleware)

  
    constructor(
         private httpBackend: HttpBackend,

        ) { };


        
    /** getbyid portfolio ***** online ***** */ 
    public getPortfolioByIdOnline(id:number | any): Observable<Portfolio> | any {

        // on fait une instance de httpClient et on empeche l'ajout de middleware
        this.httpClient = new HttpClient(this.httpBackend);
        
        return this.httpClient.get<Portfolio>(`${this.ENV_BASE}/items/${id}`);  
    }


    /** UTILS */

    getId = (id : string | any ): number => {
        return parseInt(id) ?? 0;
    }
  
  }
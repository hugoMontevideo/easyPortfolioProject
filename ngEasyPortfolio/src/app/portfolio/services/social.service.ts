import { Injectable } from "@angular/core";
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { environment } from "src/environments/environment";
import { JWTTokenService } from "src/app/services/JWTToken.service";
import { Social } from "../component/social/social.interface";
import { Observable, catchError, throwError } from "rxjs";
import { SocialAddDto } from "../component/social/social-add-dto.interface";



@Injectable()

export class SocialService {
  ENV_DEV:string = environment.apiUrl;
  tempData: string = ""; // "add" or "edit"

  constructor(
      private http: HttpClient,
      private jwtTokenService : JWTTokenService
    ) { };

  add = ( social: Social ): Observable<any> => { 
    let addSocial : SocialAddDto = {
      link: social.link,
      categorySocialId:social.categorySocialId,
      portfolioId: social.portfolioId
    }

    return this.http.post(`${this.ENV_DEV}/socials`, addSocial )
      .pipe(catchError(this.handleError)); // catch validator error
  }

  saveSocial = ( social: Social ): Observable<any> => {             
    return this.http.put( `${this.ENV_DEV}/socials/${social.id}`, social )
      .pipe(catchError(this.handleError)); // catch validator errors
  }


  
  private handleError(error: HttpErrorResponse):Observable<never>{
    return throwError(()=>error);
  }


}
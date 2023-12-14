import { Injectable } from "@angular/core";
import { Observable, catchError, map, throwError } from "rxjs";
import { HttpClient, HttpErrorResponse } from "@angular/common/http";
import { environment } from "src/environments/environment";
import { User } from "../core/user/user.interface";


@Injectable({
    providedIn: 'root'
})

export class UserService {
    ENV_DEV:string = environment.apiUrl;
    ENV_PICT:string = `${environment.apiImg}/pictures/`;
    ENV_ICONS: string = `${environment.apiIcons}/`;

    constructor ( private http: HttpClient ) {};

    getUserByEmail(email:string): Observable<any>{        
        return this.http.get<any>(`http://localhost/auth/users/${email}`);
    }

    savePicture = ( userId:number, selectedFile: File ): Observable<any> => { 
        let formData = new FormData;
        formData.append('file', selectedFile as File);

        return this.http.put(`http://localhost/auth/users/${userId}/profile_picture`, formData )
            .pipe(catchError(this.handleError)); // catch validator errors
    }
    
    saveUser = ( user: User ): Observable<any> => {             
    return this.http.put(`http://localhost/auth/users/${user.id}`, user )
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
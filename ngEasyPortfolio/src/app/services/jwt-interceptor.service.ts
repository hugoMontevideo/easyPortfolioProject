import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { Observable, catchError, throwError } from "rxjs";

@Injectable()

export class JwtInterceptorService implements HttpInterceptor {

    constructor(
        private route : Router
    ){};

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        console.log("requete intercept");
        
        let currentToken = "HelloToken";
        let storage: any = sessionStorage.getItem("currentUser");
        if( storage != null){
            currentToken = JSON.parse(storage).token;
        }
        req = req.clone({
            setHeaders:{ Authorization: `Bearer ${currentToken}`}
        });
        return next.handle(req)
        .pipe(
            catchError((error : HttpErrorResponse ) => {
                const errorCode = error.status;
                console.log(errorCode);
                if(errorCode == 403){
                    this.route.navigateByUrl("login")
                }
                return throwError(()=>error);
            }
        ));
        
    }




}
import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { Observable, catchError, throwError } from "rxjs";
import { JWTTokenService } from "./JWTToken.service";

@Injectable()

export class JwtInterceptorService implements HttpInterceptor {

    constructor(
        private route : Router,
        private jwtToken : JWTTokenService
    ){};

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
        let token: string = this.jwtToken.getToken();
       
        req = req.clone({
            setHeaders:{ Authorization: `Bearer ${token}`}
        });
        return next.handle(req)
        .pipe(
            catchError((error : HttpErrorResponse ) => {
                const errorCode = error.status;
                console.log(errorCode);
                if(errorCode == 403){
                    this.route.navigateByUrl("/login")
                }
                return throwError(()=>error);
            }
        ));
        
    }




}
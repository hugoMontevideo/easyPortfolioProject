import { inject } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivateFn, RouterStateSnapshot, Router } from "@angular/router";
import { JWTTokenService } from "../services/JWTToken.service";

export const AuthGuard: CanActivateFn = (): boolean =>{

    if(inject(JWTTokenService).isLogged()){
        return true;
      }
    inject(Router).navigateByUrl('/');
    return false;


}
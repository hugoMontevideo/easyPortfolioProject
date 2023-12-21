import { Injectable, inject } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivate, CanActivateFn, RouterStateSnapshot } from "@angular/router";
import { JWTTokenService } from "../services/JWTToken.service";


@Injectable({
    providedIn: 'root'
})
export class PermissionsService {

    constructor( private jwtTokenService : JWTTokenService ){}

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
        return this.jwtTokenService.isLogged();        
    }

}

export const AuthGuard: CanActivateFn = (next: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean =>{
    return inject(PermissionsService).canActivate(next, state);
}
import { Injectable } from '@angular/core';
import { jwtDecode } from 'jwt-decode';
import { DecodeToken } from './interfaces/decode-token.interface';


@Injectable()
export class JWTTokenService {

    jwtToken!: string;
    decodedToken!: DecodeToken ;

    constructor() {
    }

    setToken(token: string): void {
      if (token) {
        this.jwtToken = token;
      }
    }

    getToken(): string {
      let token = sessionStorage.getItem('token');
      let stringToken = token ?? "" ;      
      return stringToken;
    }

    decodeToken() {
      if (this.jwtToken) {
      this.decodedToken = jwtDecode(this.jwtToken);
      }
    }

    getDecodeToken() {
      return jwtDecode(this.jwtToken);
    }


    getUser(): string | any {
      this.decodeToken();
      return this.decodedToken ? this.decodedToken.sub : null;
    }


    // getEmailId() {
    //   this.decodeToken();
    //   return this.decodedToken ? this.decodedToken.email : null;
    // }


    getExpiryTime() {
      this.decodeToken();
      return this.decodedToken ? this.decodedToken.exp : 0;
    }


    isTokenExpired(): boolean {
      const expiryTime: number = this.getExpiryTime();
      if (expiryTime) {
        return ((1000 * expiryTime) - (new Date()).getTime()) < 5000;
      } else {
        return false;
      }
    }

    isLogged(): boolean {
      return this.getUser() != null;
    }




}



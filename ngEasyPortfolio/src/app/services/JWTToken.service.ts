import { Injectable } from '@angular/core';
import { jwtDecode } from 'jwt-decode';
import { DecodeToken } from './interfaces/decode-token.interface';


@Injectable()
export class JWTTokenService {

    jwtToken!: string;
    decodedToken!: DecodeToken ;

    constructor() {
    }

    setToken(token: string) {
      if (token) {
        this.jwtToken = token;
      }
    }


    decodeToken() {
      if (this.jwtToken) {
      this.decodedToken = jwtDecode(this.jwtToken);
      }
    }


    getDecodeToken() {
      return jwtDecode(this.jwtToken);
    }


    getUser() {
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
}



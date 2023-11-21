


import { Injectable } from '@angular/core';
import { LoginUser } from '../login/login-user.interface';

@Injectable({
  providedIn: 'root'
})
export class SessionStorageService {


  public setConButton  = (conButton: string): void => {
    sessionStorage.setItem('conbutton', conButton);
  }
  public getConButton = (): string|any => {
    return sessionStorage.getItem('conbutton');
  }


  public setLogin  = (login: string) => {
    sessionStorage.setItem('login', login);
  }
  public getLogin = () => {
   return sessionStorage.getItem("login");
  }


  public setToken = (token: string): void => {
    sessionStorage.setItem('token', token);
  }
  public getToken = (): string|any => {
    return sessionStorage.getItem('token');
  }

  public hydrate = (loginUser : LoginUser) => {
    sessionStorage.setItem('login', loginUser.login);
    sessionStorage.setItem('token', loginUser.token);
    sessionStorage.setItem("conButton", "Déconnexion")
  }

  public clearStorage = ()=> {
    sessionStorage.clear();
  }

}

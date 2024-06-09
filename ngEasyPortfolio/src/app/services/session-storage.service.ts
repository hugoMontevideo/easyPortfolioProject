import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})

export class SessionStorageService {

  tempData!: string;


  public setLogin  = (login: string): void => {
    sessionStorage.setItem('login', login);
  }
  public getLogin = (): string  => {
    let anyData = sessionStorage.getItem('login');
    let login = anyData ?? "" ;
    return login;
  }

  
  public setTempData  = (tempData: string): void => {
    sessionStorage.setItem('temp-data', tempData);
  }
  public getTempData = (): string  => {
    let anyData = sessionStorage.getItem('temp-data');
    let tempData = anyData ?? "" ;
    return tempData;
  }
  removeTempData = ():void => {
    sessionStorage.removeItem("temp-data");
    this.tempData = "";

  }


}

 // public setConButton  = (conButton: string): void => {
  //   sessionStorage.setItem('conbutton', conButton);
  // }
  // public getConButton = (): string|any => {
  //   return sessionStorage.getItem('conbutton');
  // }

   // public setToken = (token: string): void => {
  //   sessionStorage.setItem('token', token);
  // }
  // public getToken = (): string|any => {
  //   return sessionStorage.getItem('token');
  // }

  // public hydrate = (loginUser : LoginUser) => {
  //   sessionStorage.setItem('login', loginUser.email);
  //   sessionStorage.setItem('token', loginUser.token);
  //   sessionStorage.setItem("conbutton", "Déconnexion")
  // }

  // public clearStorage = (): void => {
  //   sessionStorage.removeItem('token');
  // }
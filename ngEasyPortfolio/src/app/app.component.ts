import { Component } from '@angular/core';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { faCoffee } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'ngEasyPortfolio';
  faCoffe = faCoffee;

  userEmail = "hello";
  connectionButton: boolean = false;
  
  constructor(
        private modalService: NgbModal,
      ){}

  public open(modal:any):void {
    this.modalService.open(modal);
  }

public getEmitEmail( loginEmail : {userEmail:string} ){
  
}



}

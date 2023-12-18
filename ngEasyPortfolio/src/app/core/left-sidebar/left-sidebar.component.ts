import { HttpErrorResponse } from '@angular/common/http';
import { Component, Input } from '@angular/core';
import { Portfolio } from 'src/app/portfolio/model/portfolio/portfolio.interface';
import { PortfolioService } from 'src/app/portfolio/services/portfolio.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-left-sidebar',
  templateUrl: './left-sidebar.component.html',
  styleUrls: ['./left-sidebar.component.scss']
})
export class LeftSidebarComponent {
  ENV_PICT:string = `${environment.apiImg}/pictures/`;
  ENV_ICONS: string = `${environment.apiIcons}/`;
  @Input() portfolio: Portfolio = {
                            id: -1,
                            title: "",
                            description: "",
                            name: "",
                            firstname: "",
                            email:"",
                            city: "",
                            profileImgPath: "",
                            aboutMe: "",
                            projects: [],
                            educations:[],
                            experiences:[],
                            skills: [],
                            user: {
                              id: 0,
                              name: "",
                              firstname: "",
                              email: "",
                              password: "",
                              dateInscription: new Date("1970-01-01"),
                              dateConnect: new Date("1970-01-01"),
                              profileImgPath: "",
                              roles: []
                            }
                          };
  legend: string = "Modifier 'A propos'";
  isUserFormShowing=true;
  inputError?: string;
  isPictureFormShowing=false;
  selectedFile?: File ;

  constructor ( protected portfolioService: PortfolioService ) {}

  public onModifyPicture = () => {
    this.legend = "Modifier la photo"
    this.isPictureFormShowing = true;
    this.isUserFormShowing = false;
  }
  public onModifyAbout = () => {
    this.legend = "Modifier 'A propos'"
    this.isUserFormShowing = true;
    this.isPictureFormShowing = false;
  }

  public onCloseModalForm = () => {
    this.isUserFormShowing = false;
    this.isPictureFormShowing = false;
  }

  public onSubmitUser = ()=>{
    this.portfolioService.savePortfolio(this.portfolio)
    .subscribe({
      next:(data:Portfolio)=>{
              this.isUserFormShowing = false;  // hide the form
              this.portfolio = data;              
          },
      error:(_error : any)=>{
        console.error("**error updating portfolio.aboutMe**");
        if(_error instanceof HttpErrorResponse ) {
          this.inputError = _error.error.title;
        } 
      }
    });
  }

  public onSubmitPicture = ()=>{
    this.portfolioService.savePicture(this.portfolio.id, this.selectedFile!)
    .subscribe({
      next:(data:Portfolio)=>{
        console.log(data);
              this.isPictureFormShowing = false;  // hide the form
              this.portfolio = data; 
          },
      error:(_error : any)=>{
        console.error("**error updating aboutMe Picture**");
        if(_error instanceof HttpErrorResponse ) {
          this.inputError = _error.error.title;
        } 
      }
    });
  }
  
  onSelectedFile = (event:any):void => {
    this.selectedFile = event.target.files[0];    
  }


}

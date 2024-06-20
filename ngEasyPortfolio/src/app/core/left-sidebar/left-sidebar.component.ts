import { HttpErrorResponse } from '@angular/common/http';
import { AfterViewChecked, Component, ElementRef, Input, Renderer2, SimpleChanges } from '@angular/core';
import { Portfolio } from 'src/app/portfolio/model/portfolio/portfolio.interface';
import { PortfolioService } from 'src/app/portfolio/services/portfolio.service';
import { environment } from 'src/environments/environment';
import * as Editor from 'ckeditor5-custom-build/build/ckeditor';

@Component({
  selector: 'app-left-sidebar',
  templateUrl: './left-sidebar.component.html',
  styleUrls: ['./left-sidebar.component.scss']
})
export class LeftSidebarComponent implements AfterViewChecked {
  public Editor: any = Editor;
  editorData: string = "";
 
  ENV_PICT:string = `${environment.apiImg}/pictures/`;
  ENV_ICONS: string = `${environment.apiIcons}/`;
  @Input()allDisplay: boolean= false;
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
  legend: string = "A propos";
  isUserFormShowing=true;
  inputError?: string;
  isPictureFormShowing=false;
  isLinksFormShowing=false;
  selectedFile?: File ;


  // TODO : add a link property on portfolio
  links: string = "https://test-facebook";

  constructor ( protected portfolioService: PortfolioService,
                private renderer : Renderer2,
                private elRef : ElementRef
    ) {}; 

  ngOnChanges( change: SimpleChanges){
    this.editorData = this.portfolio.aboutMe;    
  }

  ngAfterViewChecked(): void {
    const cssEditor = this.elRef.nativeElement.querySelector('.ck-content'); // ckeditor  main
    if(cssEditor){
      this.renderer.setStyle(cssEditor,'background-color', 'rgb(21%,21%,34%)');
      this.renderer.setStyle(cssEditor,'border-radius', '0 0 5px 5px');
    }
  }

  public onModifyPicture = () => {
    this.legend = "Modifier la photo"
    this.isPictureFormShowing = true;
    this.isUserFormShowing = false;
    this.isLinksFormShowing=false
  }

  public onModifyAbout = () => {
    this.legend = "Modifier 'A propos'"
    this.isUserFormShowing = true;
    this.isPictureFormShowing = false;
    this.isLinksFormShowing=false;
  }
  
  onLinks = ():void => {
    this.legend = "Modifier les liens"
    this.isLinksFormShowing=true;
    this.isPictureFormShowing=false;
    this.isUserFormShowing=false;
  }

  public onCloseModalForm = () => {
    this.isUserFormShowing = false;
    this.isPictureFormShowing = false;
    this.isLinksFormShowing=false
  }

  public onSubmitUser = ()=>{
    this.portfolio.aboutMe = this.editorData;
    
    this.portfolioService.savePortfolio(this.portfolio)
    .subscribe({
      next:(data:Portfolio)=>{
              // this.isUserFormShowing = false;  // hide the form
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

              this.isPictureFormShowing = false;  // hide the form
              this.isUserFormShowing = true; 
              this.legend = "Modifier 'A propos'"
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

  public onSubmitLinks = ()=>{
    this.isLinksFormShowing=false
  }
  
  onSelectedFile = (event:any):void => {
    this.selectedFile = event.target.files[0];    
  }



}

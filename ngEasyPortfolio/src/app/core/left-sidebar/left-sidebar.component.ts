import { HttpErrorResponse } from '@angular/common/http';
import { AfterViewChecked, Component, ElementRef, Input, Renderer2, SimpleChanges } from '@angular/core';
import { Portfolio } from 'src/app/portfolio/model/portfolio/portfolio.interface';
import { PortfolioService } from 'src/app/portfolio/services/portfolio.service';
import { environment } from 'src/environments/environment';
import * as Editor from 'ckeditor5-custom-build/build/ckeditor';
import { Social } from 'src/app/portfolio/component/social/social.interface';
import { SocialService } from 'src/app/portfolio/services/social.service';

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
                            socials: [],
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

  socialTemp:Social|undefined;

  socialGithub!: Social; 
  githubButton!: string;
  socialLinkedin!: Social;
  linkedinButton!: string;
  socialInstagram!: Social;
  instagramButton!:string;
  socialX!: Social;
  xButton!:string;
  socialFacebook!: Social;
  facebookButton!:string;

  socialOthers!: Social;


  constructor ( protected portfolioService: PortfolioService,
                private socialService: SocialService,
                private renderer : Renderer2,
                private elRef : ElementRef
    ) {}; 

  ngOnChanges( change: SimpleChanges){
    this.editorData = this.portfolio.aboutMe; 
    this.getSocialGithub();       
    this.getSocialLinkedin();       
    this.getSocialInstagram();       
    this.getSocialX();       
    this.getSocialFacebook();       
  }

  ngAfterViewChecked(): void {

    const cssEditorLeftSidebar = this.elRef.nativeElement.querySelector('#editorLeftSidebar .ck-content'); // ckeditor  main
  
    this.renderer.setStyle(cssEditorLeftSidebar,'background-color', 'rgb(21%,21%,34%)');
    this.renderer.setStyle(cssEditorLeftSidebar,'border-radius', '0 0 5px 5px');
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
  
  public onLinks = ():void => {
    this.legend = "Ajouter ou Modifier les liens"
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

  public onSubmitLinks = (social:Social)=>{
    social.portfolioId = this.portfolio.id;
    if(social.id==-1){  // add ****
      this.socialService.add(social)
      .subscribe({
        next:(data)=>{
          if(data.categorySocialId==1){
            this.githubButton="Modifier"
          } else if(data.categorySocialId==2){
            this.linkedinButton="Modifier"
          } else if(data.categorySocialId==3){
            this.instagramButton="Modifier"
          }
        },
        error:(_error)=>{
          // todo manage error  -- onSubmitSkillTxt too
          console.log("**error Adding Skill**");
          if(_error instanceof HttpErrorResponse ) {
            this.inputError = _error.error.training;
          } 
        }
      });

    } else {

      // this.newSkill.portfolioId = this.portfolioId; 
      this.socialService.saveSocial(social)  // modify ***
      .subscribe({
        next:(data)=>{
        },
        error:(_error)=>{
          console.error("**error updating Skill**");
          if(_error instanceof HttpErrorResponse ) {
            this.inputError = _error.error.title;
          } 
        }
      });
    }

  }
  
  onSelectedFile = (event:any):void => {
    this.selectedFile = event.target.files[0];    
  }

  

  private getSocialGithub = () => {
    this.socialTemp = this.portfolio.socials.find(social=>social.categorySocialId==1);    
    this.socialGithub = (this.socialTemp) ?this.socialTemp :{ id: -1,link: "",categorySocialId:1,portfolioId: -1 } ;
    this.githubButton = this.getButtonLabel(this.socialGithub.link);
  }
  private getSocialLinkedin = () => {
    this.socialTemp = this.portfolio.socials.find(social=>social.categorySocialId==2);    
    this.socialLinkedin = (this.socialTemp) ?this.socialTemp :{ id: -1,link: "",categorySocialId:2,portfolioId: -1 } ;
    this.linkedinButton = this.getButtonLabel(this.socialLinkedin.link);
  }
  private getSocialInstagram = () => {
    this.socialTemp = this.portfolio.socials.find(social=>social.categorySocialId==3);    
    this.socialInstagram = (this.socialTemp) ?this.socialTemp :{ id: -1,link: "",categorySocialId:3,portfolioId: -1 } ;
    this.instagramButton = this.getButtonLabel(this.socialInstagram.link);
  }
  private getSocialX = () => {
    this.socialTemp = this.portfolio.socials.find(social=>social.categorySocialId==4);    
    this.socialX = (this.socialTemp) ?this.socialTemp :{ id: -1,link: "",categorySocialId:4,portfolioId: -1 } ;
    this.xButton = this.getButtonLabel(this.socialX.link);
  }
  private getSocialFacebook = () => {
    this.socialTemp = this.portfolio.socials.find(social=>social.categorySocialId==5);    
    this.socialFacebook = (this.socialTemp) ?this.socialTemp :{ id: -1,link: "",categorySocialId:5,portfolioId: -1 } ;
    this.facebookButton = this.getButtonLabel(this.socialFacebook.link);
  }

  getButtonLabel = (link:string) => {
    return link && link.trim() !== '' ? 'Modifier' : 'Ajouter';
  }



}

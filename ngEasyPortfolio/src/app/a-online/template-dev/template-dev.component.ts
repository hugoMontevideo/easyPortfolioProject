import { Component, OnInit, Renderer2, ElementRef, AfterViewChecked} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Portfolio } from 'src/app/portfolio/model/portfolio/portfolio.interface';
import { PortfolioService } from 'src/app/portfolio/services/portfolio.service';
import { environment } from 'src/environments/environment';
import { ViewportScroller } from '@angular/common';
import * as Editor from 'ckeditor5-custom-build/build/ckeditor';
import { Social } from 'src/app/portfolio/component/social/social.interface';
//import { Skill } from 'src/app/portfolio/component/skill/skill.interface';

@Component({
  selector: 'app-template-dev',
  templateUrl: './template-dev.component.html',
  styleUrls: ['./template-dev.component.scss']
})

export class TemplateDevComponent implements OnInit, AfterViewChecked {
  
  ENV_ICONS: string = `${environment.apiIcons}/`;
  ENV_PICT:string = `${environment.apiImg}/pictures/`;
  burger = false;
  
  portfolio: Portfolio = {
        id: -1,
        title: "",
        description: "",
        name: "",
        firstname: "",
        email:"",
        profileImgPath: "",
        aboutMe: "",
        city: "",
        projects: [],
        educations:[],
        experiences:[],
        skills: [],
        socials: [],
  }
  
  public Editor2: any = Editor;
  editorData: string = "";
 
  socialTemp:Social|undefined;

  socialGithub!: Social; 
  socialLinkedin!: Social;
  socialInstagram!: Social;
  socialX!: Social;
  socialFacebook!: Social;
  socialOthers!: Social;

  constructor(
    private route: ActivatedRoute,
    private portfolioService: PortfolioService,
    private viewportScroller : ViewportScroller,
    private renderer2 : Renderer2,
    private elRef2 : ElementRef
  ){};

  ngOnInit(): void {
    this.portfolio.id = this.portfolioService.getId(this.route.snapshot.paramMap.get('id'));
    this.portfolioService.getPortfolioByIdOnline(this.portfolio.id)
      .subscribe({
        next:(response:Portfolio) => { 
                this.portfolio = response;
                this.editorData = this.portfolio.aboutMe;
                this.getSocialGithub();
                this.getSocialLinkedin();
                this.getSocialInstagram();
                this.getSocialX();
                this.getSocialfacebook();
              }, 
        error: (err:Error) => {
                  // TODO  manage error response
                  console.error("Error portfolioById")
              }
      });
  }

  ngAfterViewChecked(): void {
    const cssEditor2 = this.elRef2.nativeElement.querySelector('#editor2 .ck-content'); // ckeditor  main
    const cssEditorTop = this.elRef2.nativeElement.querySelector('#editor2 .ck-editor__top'); // ckeditor  top

    this.renderer2.setStyle(cssEditor2,'background-color', 'transparent');
    this.renderer2.setStyle(cssEditor2,'border', 'none');
    this.renderer2.setStyle(cssEditorTop,'display', 'none');
  }

  onBurger = ()=>{
    this.burger=!this.burger;    
  }

  scrollToSection = (sectionId : string):void =>{   
    this.viewportScroller.scrollToAnchor(sectionId);
    this.burger=false;
  }

  private getSocialGithub = () => {
    this.socialTemp = this.portfolio.socials.find(social=>social.categorySocialId==1);
    this.socialGithub = (this.socialTemp) ?this.socialTemp :{ id: -1,link: "",categorySocialId:1,portfolioId: -1 } ;
  }
  private getSocialLinkedin = () => {
    this.socialTemp = this.portfolio.socials.find(social=>social.categorySocialId==2);
    this.socialLinkedin = (this.socialTemp) ?this.socialTemp :{ id: -1,link: "",categorySocialId:2,portfolioId: -1 } ;
  }
  private getSocialInstagram = () => {
    this.socialTemp = this.portfolio.socials.find(social=>social.categorySocialId==3);
    this.socialInstagram = (this.socialTemp) ?this.socialTemp :{ id: -1,link: "",categorySocialId:3,portfolioId: -1 } ;
  }
  private getSocialX = () => {
    this.socialTemp = this.portfolio.socials.find(social=>social.categorySocialId==4);
    this.socialX = (this.socialTemp) ?this.socialTemp :{ id: -1,link: "",categorySocialId:4,portfolioId: -1 } ;
  }
  private getSocialfacebook = () => {
    this.socialTemp = this.portfolio.socials.find(social=>social.categorySocialId==5);
    this.socialFacebook = (this.socialTemp) ?this.socialTemp :{ id: -1,link: "",categorySocialId:5,portfolioId: -1 } ;
  }

}


// softSkills!: Skill[]; // display purpose array
  // languages!: Skill[]; // display purpose array
  // programsLanguages!: Skill[]; // display purpose array
  // drivingLicenceCategories!: Skill[]; // display purpose array
  // otherSkills!: Skill[];

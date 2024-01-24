import { Component, OnInit, Renderer2, ElementRef, AfterViewChecked} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Portfolio } from 'src/app/portfolio/model/portfolio/portfolio.interface';
import { PortfolioService } from 'src/app/portfolio/services/portfolio.service';
import { environment } from 'src/environments/environment';
import { ViewportScroller } from '@angular/common';
import { Skill } from 'src/app/portfolio/component/skill/skill.interface';
import * as Editor from 'ckeditor5-custom-build/build/ckeditor';

@Component({
  selector: 'app-template-dev',
  templateUrl: './template-dev.component.html',
  styleUrls: ['./template-dev.component.scss']
})

export class TemplateDevComponent implements OnInit, AfterViewChecked {
  
  ENV_ICONS: string = `${environment.apiIcons}/`;
  ENV_PICT:string = `${environment.apiImg}/pictures/`;
  burger = false;
  
  softSkills!: Skill[]; // display purpose array
  languages!: Skill[]; // display purpose array
  programsLanguages!: Skill[]; // display purpose array
  drivingLicenceCategories!: Skill[]; // display purpose array
  otherSkills!: Skill[];

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
  }
  
  public Editor: any = Editor;
  editorData: string = "";

  constructor(
    private route: ActivatedRoute,
    private portfolioService: PortfolioService,
    private viewportScroller : ViewportScroller,
    private renderer : Renderer2,
    private elRef : ElementRef
  ){};

  ngOnInit(): void {
 
    
    // this.renderer.setStyle(cssEditor, 'background-color', 'transparent');
    this.portfolio.id = this.portfolioService.getId(this.route.snapshot.paramMap.get('id'));
    this.portfolioService.getPortfolioByIdOnline(this.portfolio.id)
      .subscribe({
        next:(response:Portfolio) => { 
                                    this.portfolio = response;
                                    this.softSkills = this.portfolio.skills.filter(item=>item.categorySkillId==1); 
                                    this.languages = this.portfolio.skills.filter(item=>item.categorySkillId==2); 
                                    this.programsLanguages = this.portfolio.skills.filter(item=>item.categorySkillId==3); 
                                    this.drivingLicenceCategories = this.portfolio.skills.filter(item=>item.categorySkillId==4);
                                    this.otherSkills = this.portfolio.skills.filter(item=>item.categorySkillId==5); 
                                    this.editorData = this.portfolio.aboutMe;                                                                                                   
                        }, 
        error: (err:Error) => {
                        // TODO  manage error response
                        console.error("Error portfolioById")
                    }
    });
  }

  ngAfterViewChecked(): void {
    const cssEditor = this.elRef.nativeElement.querySelector('.ck-content'); // ckeditor  main
    const cssEditorTop = this.elRef.nativeElement.querySelector('.ck-editor__top'); // ckeditor  top
    if(cssEditor){
      this.renderer.setStyle(cssEditor,'background-color', 'transparent');
      this.renderer.setStyle(cssEditor,'border', 'none');
      this.renderer.setStyle(cssEditorTop,'display', 'none');
    }
  }

  onBurger = ()=>{
    this.burger=!this.burger;    
  }

  scrollToSection = (sectionId : string):void =>{   
    this.viewportScroller.scrollToAnchor(sectionId);
    this.burger=false;
  }



}

import { Component, OnInit, Renderer2, ElementRef, AfterViewChecked, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Portfolio } from 'src/app/portfolio/model/portfolio/portfolio.interface';
import { PortfolioService } from 'src/app/portfolio/services/portfolio.service';
import { environment } from 'src/environments/environment';
import { ViewportScroller } from '@angular/common';
import * as Editor from 'ckeditor5-custom-build/build/ckeditor';
import { Social } from 'src/app/portfolio/component/social/social.interface';

@Component({
  selector: 'app-template-dev',
  templateUrl: './template-dev.component.html',
  styleUrls: ['./template-dev.component.scss']
})

export class TemplateDevComponent implements OnInit, AfterViewChecked {

  ENV_ICONS: string = `${environment.apiIcons}/`;
  ENV_PICT:string = `${environment.apiImg}/pictures/`;
  burger = false;

  @Input() portfolio: Portfolio = {
                    id: -1,
                    title: "",
                    description: "",
                    name: "",
                    firstname: "hell",
                    email:"",
                    profileImgPath: "",
                    aboutMe: "",
                    city: "",
                    projects: [],
                    educations:[],
                    experiences:[],
                    skills: [],
                    socials: [],
                    categoryPortfolioId: -1,
                }
  
  @Input() socialGithub!: Social;
  @Input() socialLinkedin!: Social;
  @Input() socialInstagram!: Social;
  @Input() socialX!: Social;
  @Input() socialFacebook!: Social;

  public Editor2: any = Editor;
  editorData: string = "";

  constructor(
    private route: ActivatedRoute,
    private portfolioService: PortfolioService,
    private viewportScroller : ViewportScroller,
    private renderer2 : Renderer2,
    private elRef2 : ElementRef
  ){};

  ngOnInit(): void {
    this.editorData = this.portfolio.aboutMe;
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


}


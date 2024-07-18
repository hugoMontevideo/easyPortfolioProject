import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Portfolio } from 'src/app/portfolio/model/portfolio/portfolio.interface';
import { OnlineService } from 'src/app/services/online.service';
import * as Editor from 'ckeditor5-custom-build/build/ckeditor';
import { Social } from 'src/app/portfolio/component/social/social.interface';


@Component({
  selector: 'app-online-router',
  templateUrl: './online-router.component.html',
  styleUrl: './online-router.component.scss'
})
export class OnlineRouterComponent implements OnInit {

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
      categoryPortfolioId: -1,
  }
 
  socialTemp:Social|undefined;

  socialGithub!: Social; 
  socialLinkedin!: Social;
  socialInstagram!: Social;
  socialX!: Social;
  socialFacebook!: Social;
  socialOthers!: Social;

  constructor(
    private route: ActivatedRoute,
    private onlineService: OnlineService,
  ){};

  ngOnInit(): void {
    this.portfolio.id = this.onlineService.getId(this.route.snapshot.paramMap.get('id'));
    this.onlineService.getPortfolioByIdOnline(this.portfolio.id)
      .subscribe({
        next:(response:Portfolio) => {     
                this.portfolio = response;
                // this.editorData = this.portfolio.aboutMe;
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

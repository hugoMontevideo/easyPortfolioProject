import { Component, Input, OnChanges, SimpleChanges } from '@angular/core';
import { Social } from 'src/app/portfolio/component/social/social.interface';
import { Portfolio } from 'src/app/portfolio/model/portfolio/portfolio.interface';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-template-classic',
  standalone: true,
  imports: [],
  templateUrl: './template-classic.component.html',
  styleUrl: './template-classic.component.scss'
})
export class TemplateClassicComponent implements OnChanges {

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


  ngOnChanges( change: SimpleChanges){
    // this.newSkill.portfolioId = this.portfolioId;
    // this.getSkill6();
    // this.getSkillsWithout6();    
    // this.editorData = this.textMsg;
  }


}

import { Component, Input } from '@angular/core';
import { Social } from 'src/app/portfolio/component/social/social.interface';
import { Portfolio } from 'src/app/portfolio/model/portfolio/portfolio.interface';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-template-dev-footer',
  templateUrl: './template-dev-footer.component.html',
  styleUrl: './template-dev-footer.component.scss'
})
export class TemplateDevFooterComponent {
  ENV_ICONS: string = `${environment.apiIcons}/`;
  ENV_PICT:string = `${environment.apiImg}/pictures/`;

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

}

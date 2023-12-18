import { NgModule } from '@angular/core';
import { CommonModule, DatePipe } from '@angular/common';
import { PortfolioRoutingModule } from './portfolio-routing.module';
import { PortfolioService } from './services/portfolio.service';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { FormsModule } from '@angular/forms';
import { SkillService } from './services/skill.service';
import { ProjectService } from './services/project.service';
import { EducationService } from './services/education.service';
import { ExperienceService } from './services/experience.service';
import { EducationComponent } from './component/education/education.component';
import { ExperienceComponent } from './component/experience/experience.component';
import { PortfolioListComponent } from './component/portfolio-list/portfolio-list.component';
import { PortfolioListItemComponent } from './component/portfolio-list-item/portfolio-list-item.component';
import { ProjectComponent } from './component/project/project.component';
import { SkillComponent } from './component/skill/skill.component';
import { BoardProjectComponent } from './board/board-project/board-project.component';
import { BoardSkillComponent } from './board/board-skill/board-skill.component';
import { BoardEducationComponent } from './board/board-education/board-education.component';
import { BoardExperienceComponent } from './board/board-experience/board-experience.component';
import { HomeNavComponent } from '../core/home-nav/home-nav.component';
import { UserItemComponent } from '../core/user-item/user-item.component';
import { FooterComponent } from '../core/footer/footer.component';
import { LeftSidebarComponent } from '../core/left-sidebar/left-sidebar.component';

@NgModule({
  declarations: [
    EducationComponent,
    ExperienceComponent,
    PortfolioListComponent,
    PortfolioListItemComponent,
    ProjectComponent,
    SkillComponent,
    BoardProjectComponent,
    BoardSkillComponent,
    BoardEducationComponent,
    BoardExperienceComponent,
    HomeNavComponent,
    UserItemComponent,
    FooterComponent,
    LeftSidebarComponent

  ],
  imports: [
    CommonModule,
    PortfolioRoutingModule,
    FontAwesomeModule,
    FormsModule
  ],
  exports:[
    HomeNavComponent,
    FooterComponent,
    LeftSidebarComponent
  ],
  providers: [
    PortfolioService, 
    SkillService,
    ProjectService,
    EducationService,
    ExperienceService,
    DatePipe
  ]
})
export class PortfolioModule { }

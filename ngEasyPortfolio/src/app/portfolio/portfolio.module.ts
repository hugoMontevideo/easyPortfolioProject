import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PortfolioRoutingModule } from './portfolio-routing.module';
import { PortfolioService } from './services/portfolio.service';
import { PortfolioListComponent } from './component/portfolio-list/portfolio-list.component';
import { PortfolioListItemComponent } from './component/portfolio-list-item/portfolio-list-item.component';
import { SkillComponent } from './component/skill/skill.component';
import { DashboardNavComponent } from './dashboard-nav/dashboard-nav.component';
import { ExperienceComponent } from './component/experience/experience.component';
import { EducationComponent } from './component/education/education.component';
import { ProjectComponent } from './component/project/project.component';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { SkillFormComponent } from './component/skill-form/skill-form.component';
import { FormsModule } from '@angular/forms';
import { SkillService } from './services/skill.service';


@NgModule({
  declarations: [
    PortfolioListComponent,
    PortfolioListItemComponent,
    SkillComponent,
    DashboardNavComponent,
    ExperienceComponent,
    EducationComponent,
    ProjectComponent,
    SkillFormComponent
  ],
  imports: [
    CommonModule,
    PortfolioRoutingModule,
    FontAwesomeModule,
    FormsModule
  ],
  providers: [
    PortfolioService, 
    SkillService
  ]
})
export class PortfolioModule { }
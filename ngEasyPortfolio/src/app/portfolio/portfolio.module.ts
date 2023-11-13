import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PortfolioRoutingModule } from './portfolio-routing.module';
import { PortfolioService } from './services/portfolio.service';
import { PortfolioListComponent } from './component/portfolio-list/portfolio-list.component';
import { PortfolioListItemComponent } from './component/portfolio-list-item/portfolio-list-item.component';
import { SkillComponent } from './component/skill/skill.component';


@NgModule({
  declarations: [
    PortfolioListComponent,
    PortfolioListItemComponent,
    SkillComponent
  ],
  imports: [
    CommonModule,
    PortfolioRoutingModule
  ],
  providers: [
    PortfolioService, 
  ]
})
export class PortfolioModule { }

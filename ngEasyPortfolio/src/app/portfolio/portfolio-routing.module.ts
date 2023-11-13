import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PortfolioListComponent } from './component/portfolio-list/portfolio-list.component';
import { PortfolioListItemComponent } from './component/portfolio-list-item/portfolio-list-item.component';

const routes: Routes = [
  { path: '', component: PortfolioListComponent },
  { path: ':id', component: PortfolioListItemComponent },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})

export class PortfolioRoutingModule { }

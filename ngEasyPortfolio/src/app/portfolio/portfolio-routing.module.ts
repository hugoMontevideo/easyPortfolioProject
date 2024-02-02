import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PortfolioListComponent } from './component/portfolio-list/portfolio-list.component';
import { PortfolioListItemComponent } from './component/portfolio-list-item/portfolio-list-item.component';
import { AuthGuard } from '../guards/auth.guard';

const routes: Routes = [
  { path: '', component: PortfolioListComponent, canActivate:[AuthGuard] },
  { path: ':id', component: PortfolioListItemComponent, canActivate:[AuthGuard] },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})

export class PortfolioRoutingModule { }

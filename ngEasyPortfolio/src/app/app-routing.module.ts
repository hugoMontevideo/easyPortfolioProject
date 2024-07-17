import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { UserComponent } from './core/user/user.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { OnlineRouterComponent } from './a-online/online-router/online-router.component';

const routes: Routes = [
  { path: "", component: HomeComponent},
  { path: "login", component: LoginComponent},
  { path: "user/:id", component: UserComponent},
  { path: "register", component: RegisterComponent},

  { path: "portfolios", loadChildren:()=>import("./portfolio/portfolio.module").then(m=>m.PortfolioModule)},
  // exemples, modeles, templates
  { path: "item/:id", component: OnlineRouterComponent},

  { path: "**", redirectTo:""},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

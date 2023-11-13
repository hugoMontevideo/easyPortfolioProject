import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { UserComponent } from './user/user.component';
import { LoginComponent } from './login/login.component';
import { ConnectionComponent } from './connection/connection.component';

const routes: Routes = [
  { path: "", component: HomeComponent},
  { path: "login", component: LoginComponent},
  { path: "user", component: UserComponent},
  { path: "user/:id", component: UserComponent},
  { path: "connection", component: ConnectionComponent},

  { path: "portfolio", loadChildren:()=>import("./portfolio/portfolio.module").then(m=>m.PortfolioModule)},
];

@NgModule({
  imports: [RouterModule.forRoot(routes )], //***** usehash à enlever  */
  // imports: [RouterModule.forRoot(routes, {useHash:true} )], //***** usehash à enlever  */
  exports: [RouterModule]
})
export class AppRoutingModule { }

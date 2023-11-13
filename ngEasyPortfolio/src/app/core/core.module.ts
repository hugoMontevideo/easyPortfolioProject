import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeNavComponent } from './home-nav/home-nav.component';
import { FormsModule } from '@angular/forms';
import { SharedModule } from '../shared/shared.module';
import { AppRoutingModule } from '../app-routing.module';



@NgModule({
  declarations: [
    HomeNavComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    SharedModule,
    AppRoutingModule

  ],
  exports: [
    HomeNavComponent,
  ]
})
export class CoreModule { }

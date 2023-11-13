import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FontAwesomeModule, FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { fas } from '@fortawesome/free-solid-svg-icons';


@NgModule({
  declarations: [],
  imports: [
    CommonModule,
    FontAwesomeModule,
  ],
  exports: [
    FontAwesomeModule,
  ]
})
export class SharedModule {
  
  constructor(library: FaIconLibrary){
    library.addIconPacks(fas);
    // library.addIconPacks(faCoffee);
  }
 }

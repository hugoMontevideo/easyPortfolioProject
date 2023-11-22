import { Component, Input } from '@angular/core';
// import { FontAwesomeModule, FaIconLibrary } from '@fortawesome/angular-fontawesome';


@Component({
  selector: 'app-home-nav',
  templateUrl: './home-nav.component.html',
  styleUrls: ['./home-nav.component.scss']
})
export class HomeNavComponent {
  
  @Input()  conButton: String|any;
  @Input()  login: String|any;
  
}

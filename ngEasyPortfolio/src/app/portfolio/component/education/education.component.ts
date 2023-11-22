import { Component, Input } from '@angular/core';
import { Education } from './education.interface';

@Component({
  selector: 'app-education',
  templateUrl: './education.component.html',
  styleUrls: ['./education.component.scss']
})
export class EducationComponent {

  @Input() education!:Education;
}

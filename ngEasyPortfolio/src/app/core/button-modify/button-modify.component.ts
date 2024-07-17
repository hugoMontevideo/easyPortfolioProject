import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-button-modify',
  templateUrl: './button-modify.component.html',
  styleUrl: './button-modify.component.scss'
})
export class ButtonModifyComponent {

  @Output() buttonClicked = new EventEmitter<void>();

  onClick=():void => {
    this.buttonClicked.emit();
  }

}

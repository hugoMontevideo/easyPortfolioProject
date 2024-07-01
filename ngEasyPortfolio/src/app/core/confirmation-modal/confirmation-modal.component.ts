import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { ConfirmationModalService } from 'src/app/services/confirmation-modal.service';

@Component({
  selector: 'app-confirmation-modal',
  templateUrl: './confirmation-modal.component.html',
  styleUrls: ['./confirmation-modal.component.scss']
})

export class ConfirmationModalComponent implements OnInit {

  @Output() confirmed = new EventEmitter<boolean>();
  //@ViewChild('modal') modal!: ElementRef;

  constructor(private confirmationModalService: ConfirmationModalService) {}

  ngOnInit() {
    this.confirmationModalService.confirmation$.subscribe((result: boolean) => {
      this.confirmed.emit(result);
      // this.closeModal();
    });
  }



  confirm() {
    this.confirmationModalService.confirm();
  }

  decline() {
    this.confirmationModalService.decline();
  }

}




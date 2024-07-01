import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({ providedIn: 'root'})

export class ConfirmationModalService {
  private confirmationSubject = new Subject<boolean>();
  confirmation$ = this.confirmationSubject.asObservable();

  confirm() {
    this.confirmationSubject.next(true);
  }

  decline() {
    this.confirmationSubject.next(false);
  }
}

import { Injectable } from '@angular/core';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';

@Injectable()
export class MessageService {

  private messageSource = new BehaviorSubject<string>("");

  currentMessage = this.messageSource.asObservable();

  constructor() { }

  passMessage(message: string) {
    this.messageSource.next(message);
  }
}

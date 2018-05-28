import { Injectable, EventEmitter } from '@angular/core';

@Injectable()
export class NotificationService {

  displayNotificationEmitter: EventEmitter<string>;

  constructor() {
    this.displayNotificationEmitter = new EventEmitter<string>();
  }

  displayNotification(notification: string) {
    this.displayNotificationEmitter.emit(notification);
  }
}

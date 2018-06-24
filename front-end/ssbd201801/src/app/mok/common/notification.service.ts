import { Injectable, EventEmitter } from '@angular/core';

@Injectable()
export class NotificationService {

  notificationEmitter: EventEmitter<string>;

  constructor() {
    this.notificationEmitter = new EventEmitter<string>();
  }

  displayTranslatedNotification(notification: string) {
    this.notificationEmitter.emit(notification);
  }
}

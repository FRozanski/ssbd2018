import { Component, OnInit } from '@angular/core';
import { NotificationService } from '../common/notification.service';
import { not } from '@angular/compiler/src/output/output_ast';

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.css']
})
export class NotificationsComponent implements OnInit {

  private notificationTimeout = 3000;
  isNotificationDisplayed: boolean = false;
  public notification: string;

  constructor(private notificationService: NotificationService) { }

  ngOnInit() {
    this.notificationService.displayNotificationEmitter.subscribe((notification) => {
      this.notification = notification;
      this.isNotificationDisplayed = true;

      setTimeout(() => {
        this.isNotificationDisplayed = false;
      }, this.notificationTimeout);

    });
  }

}

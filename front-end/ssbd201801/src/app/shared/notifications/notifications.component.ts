import { Component, OnInit } from '@angular/core';
import { not } from '@angular/compiler/src/output/output_ast';
import { NotificationService } from '../../mok/common/notification.service';

@Component({
  selector: 'app-notifications',
  templateUrl: './notifications.component.html',
  styleUrls: ['./notifications.component.css']
})
export class NotificationsComponent implements OnInit {

  private notificationDisplayTime = 15000;
  isNotificationDisplayed: boolean = false;
  public notification: string;

  constructor(private notificationService: NotificationService) { }

  ngOnInit() {
    this.notificationService.displayNotificationEmitter.subscribe((notification) => {
      this.notification = notification;
      this.isNotificationDisplayed = true;

      setTimeout(() => {
        this.isNotificationDisplayed = false;
      }, this.notificationDisplayTime);

    });
  }

}

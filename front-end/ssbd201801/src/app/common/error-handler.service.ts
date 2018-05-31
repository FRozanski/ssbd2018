import { Injectable } from '@angular/core';
import {NotificationService} from './notification.service';
import {Router} from '@angular/router';
import {HttpErrorResponse} from '@angular/common/http';

@Injectable()
export class ErrorHandlerService {

  constructor(
    private notificationService: NotificationService,
    private router: Router
  ) { }

  public handleError(error: any) {
    if (error instanceof HttpErrorResponse) {
      if (!navigator.onLine) {
        return this.notificationService.displayNotification('No Internet Connection');
      }
    } else {
      if (error.status !== 400)
        this.router.navigate(['/error'], { queryParams: error });
    }
  }
}

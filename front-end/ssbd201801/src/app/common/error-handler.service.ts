import { Injectable } from '@angular/core';
import {MatSnackBar} from '@angular/material';
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
    console.log('err.status = ' + error.status);
    console.log('err.message = ' + error.message);

    if (error instanceof HttpErrorResponse) {
      if (!navigator.onLine) {
        return this.notificationService.displayNotification('No Internet Connection');
      }
    } else {
      this.router.navigate(['/error'], { queryParams: error });
    }
  }
}

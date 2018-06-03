import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {HttpErrorResponse} from '@angular/common/http';
import {NotificationService} from '../../mok/common/notification.service';

@Injectable()
export class ErrorHandlerService {

  constructor(
    private notificationService: NotificationService,
    private router: Router
  ) {
  }

  public handleError(error: any) {
    if (error instanceof HttpErrorResponse) {
      if (!navigator.onLine) {
        return this.notificationService.displayTranslatedNotification('ERROR.NO_INTERNET_CONNECTION');
      }
    } else {
      this.router.navigate(['/error'], {queryParams: error});
    }
  }
}

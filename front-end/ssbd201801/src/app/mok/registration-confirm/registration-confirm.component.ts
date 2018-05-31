import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import 'rxjs/add/operator/filter';
import {TranslateService} from '@ngx-translate/core';
import {AccountService} from '../common/account.service';
import { NotificationService } from '../common/notification.service';

@Component({
  selector: 'app-registration-confirm',
  templateUrl: './registration-confirm.component.html',
  styleUrls: ['./registration-confirm.component.css']
})
export class RegistrationConfirmComponent implements OnInit {
  token: string;
  confirmationMessage = '';

  constructor (
    private accountService: AccountService,
    private route: ActivatedRoute,
    private translateService: TranslateService,
    private notificationService: NotificationService
  ) { }

  ngOnInit() {
    this.route.queryParams
      .filter(params => params.token)
      .subscribe(params => {
        this.token = params.token;

        this.accountService.confirmAccountByToken(this.token)
          .subscribe(() => {
            
            this.confirmationMessage = this.translateService.instant('REGISTER.ACCOUNT_CONFIRMED');
          }, (errorResponse) => {
            this.confirmationMessage = this.translateService.instant('REGISTER.ACCOUNT_NOT_CONFIRMED');
            this.notificationService.displayTranslatedNotification(errorResponse.error.message);
          });
      }, (errorResponse) => {
        this.notificationService.displayTranslatedNotification(errorResponse.error.message);
      });
  }
}

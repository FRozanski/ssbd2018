import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import 'rxjs/add/operator/filter';
import {TranslateService} from '@ngx-translate/core';
import {AccountService} from '../common/account.service';
import {LocationService} from '../common/location.service';
import {MatDialog, MatDialogRef} from '@angular/material';
import {ConfirmDialogComponent} from 'app/shared/confirm-dialog/confirm-dialog.component';
import {NotificationService} from '../common/notification.service';

@Component({
  selector: 'app-registration-confirm',
  templateUrl: './registration-confirm.component.html',
  styleUrls: ['./registration-confirm.component.css']
})
export class RegistrationConfirmComponent implements OnInit {
  token: string;
  confirmationMessage = '';

  dialogRef: MatDialogRef<ConfirmDialogComponent>;

  constructor (
    private accountService: AccountService,
    private route: ActivatedRoute,
    private translateService: TranslateService,
    private locationService: LocationService,
    private router: Router,
    public dialog: MatDialog,
    private notificationService: NotificationService
  ) { }

  ngOnInit() {
    this.locationService.passRouter('LOCATION.REGISTRATION_CONFIRM');

    this.dialogRef = this.dialog.open(ConfirmDialogComponent, {
      disableClose: false
    });

    this.dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.route.queryParams
          .subscribe(params => {
            this.token = params.token;
            if (this.token === undefined) {
              this.confirmationMessage = this.translateService.instant('REGISTER.NO_TOKEN_PROVIDED');
            }
            this.accountService.confirmAccountByToken(this.token)
              .subscribe(() => {
                this.notificationService.displayTranslatedNotification('REGISTER.ACCOUNT_CONFIRMED');
                this.router.navigate(['/main']);
              }, (errorResponse) => {
                this.notificationService.displayTranslatedNotification('REGISTER.ACCOUNT_NOT_CONFIRMED');
                this.confirmationMessage = this.translateService.instant(errorResponse.error.message);
              });
          }, (errorResponse) => {
            this.confirmationMessage = this.translateService.instant(errorResponse.error.message);
          });
      }
      this.dialogRef = null;
    });
  }
}

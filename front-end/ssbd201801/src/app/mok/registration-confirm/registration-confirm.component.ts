import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import 'rxjs/add/operator/filter';
import {TranslateService} from '@ngx-translate/core';
import {AccountService} from '../common/account.service';
import {LocationService} from '../common/location.service';
import {MatDialog, MatDialogRef} from '@angular/material';
import {ConfirmDialogComponent} from 'app/shared/confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-registration-confirm',
  templateUrl: './registration-confirm.component.html',
  styleUrls: ['./registration-confirm.component.css']
})
export class RegistrationConfirmComponent implements OnInit {
  token: string;
  validationMessage = '';
  confirmationMessage = '';

  dialogRef: MatDialogRef<ConfirmDialogComponent>;

  constructor (
    private accountService: AccountService,
    private route: ActivatedRoute,
    private translateService: TranslateService,
    private locationService: LocationService,
    private router: Router,
    public dialog: MatDialog
  ) { }

  ngOnInit() {
    this.locationService.passRouter(
      this.translateService.instant('LOCATION.YOUR_LOCATION') + ': ' +
      this.translateService.instant('LOCATION.REGISTRATION_CONFIRM'));

    this.dialogRef = this.dialog.open(ConfirmDialogComponent, {
      disableClose: false
    });
    this.dialogRef.componentInstance.confirmMessage = this.translateService.instant('DIALOG.ARE_YOU_SURE');

    this.dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.confirmationMessage = this.translateService.instant('REGISTER.NO_TOKEN_PROVIDED');
        this.route.queryParams
          .filter(params => params.token)
          .subscribe(params => {
            this.token = params.token;
            this.accountService.confirmAccountByToken(this.token)
              .subscribe(() => {
                this.validationMessage = '';
                this.confirmationMessage = this.translateService.instant('REGISTER.ACCOUNT_CONFIRMED');
              }, (errorResponse) => {
                this.confirmationMessage = this.translateService.instant('REGISTER.ACCOUNT_NOT_CONFIRMED');
                this.validationMessage = this.translateService.instant(errorResponse.error.message);
              });
          }, (errorResponse) => {
            this.validationMessage = this.translateService.instant(errorResponse.error.message);
          });
      }
      this.dialogRef = null;
    });
  }
}

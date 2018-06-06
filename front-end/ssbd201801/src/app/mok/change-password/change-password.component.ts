import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {AccountService} from '../common/account.service';
import {Router} from '@angular/router';
import {Location} from '@angular/common';
import {TranslateService} from '@ngx-translate/core';
import {AccountData} from '../model/account-data';
import {SessionService} from '../common/session.service';
import {MatDialog, MatDialogRef} from '@angular/material';
import {LocationService} from '../common/location.service';
import {ConfirmDialogComponent} from 'app/shared/confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {

  form: FormGroup;

  wasFormSent = false;

  formValidationMessage = '';

  userIdentity: AccountData = {};

  myAccountToEdit: AccountData = {};

  dialogRef: MatDialogRef<ConfirmDialogComponent>;

  constructor(private accountService: AccountService, private sessionService: SessionService,
              private location: Location, private translateService: TranslateService,
              private router: Router, public dialog: MatDialog,
              private locationService: LocationService) { }
  ngOnInit() {
    this.locationService.passRouter(
      this.translateService.instant('LOCATION.YOUR_LOCATION') + ': ' +
      this.translateService.instant('LOCATION.CHANGE_PASSWORD_PAGE'));

    this.sessionService.getMyIdentity().subscribe((data: AccountData) => {
      this.userIdentity = data;
    });

    this.accountService.getMyAccountToEdit().subscribe((account: AccountData) => {
      this.myAccountToEdit = account;
    });

    this.initializeForm();
  }

  sendForm() {
    this.wasFormSent = true;

    if (this.form.valid) {

      this.dialogRef = this.dialog.open(ConfirmDialogComponent, {
          disableClose: false
        });

          this.dialogRef.afterClosed().subscribe(result => {
            if (result) {
          const account: AccountData = <AccountData>this.form.value;
          account.id = this.myAccountToEdit.id;
          account.version = this.myAccountToEdit.version;
          this.accountService.changeMyPassword(account).subscribe(() => {
              alert(this.translateService.instant('SUCCESS.CHANGE_PASSWORD'));
              this.router.navigate(['/main']);
            },
            (errorResponse) => {
              this.formValidationMessage = this.translateService.instant(errorResponse.error.message);
            });
        }
        this.dialogRef = null;
      });
    }
  }

  onReturnClick() {
    this.location.back();
  }

  isRequiredSatisfied(controlName: string) {
    return (this.form.get(controlName).errors && this.form.get(controlName).errors.required) && ((this.form.get(controlName).dirty || this.form.get(controlName).touched) || this.wasFormSent);
  }

  private initializeForm() {
    this.form = new FormGroup({
      oldPassword: new FormControl('', [
        Validators.required
      ]),
      firstPassword: new FormControl('', [
        Validators.required
      ]),
      secondPassword: new FormControl('', [
        Validators.required
      ])
    });
  }
}

import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {AccountData} from '../model/account-data';
import {AccountService} from '../common/account.service';
import {Router} from '@angular/router';
import {Location} from '@angular/common';
import {TranslateService} from '@ngx-translate/core';
import {LocationService} from '../common/location.service';
import {MatDialog, MatDialogRef} from '@angular/material';
import { ConfirmDialogComponent } from '../../shared/confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-change-others-password',
  templateUrl: './change-others-password.component.html',
  styleUrls: ['./change-others-password.component.css']
})
export class ChangeOthersPasswordComponent implements OnInit {

  form: FormGroup;

  wasFormSent = false;

  formValidationMessage = '';

  othersIdDb: number;

  accountToEdit: AccountData = {};

  dialogRef: MatDialogRef<ConfirmDialogComponent>;

  constructor(private accountService: AccountService, private location: Location,
              private translateService: TranslateService, private router: Router,
              private locationService: LocationService, public dialog: MatDialog) { }

  ngOnInit() {
    this.locationService.passRouter('LOCATION.CHANGE_OTHERS_PASSWORD_PAGE');
    this.accountService.currentId.subscribe(id => this.othersIdDb = id);

    this.accountService.getAccountToEdit(this.othersIdDb).subscribe((data: AccountData) => {
      this.accountToEdit = data;
    },
      (errorResponse) => {
        this.formValidationMessage = this.translateService.instant(errorResponse.error.message);
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
          account.id = this.accountToEdit.id;
          account.version = this.accountToEdit.version;
          this.accountService.changeOthersPassword(account).subscribe(() => {
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
      firstPassword: new FormControl('', [
        Validators.required
      ]),
      secondPassword: new FormControl('', [
        Validators.required
      ])
    });
  }

}

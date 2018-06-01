import { Component, OnInit, ChangeDetectorRef, OnChanges, ElementRef, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { AccountService } from '../common/account.service';
import { AccountData } from '../model/account-data';
import { HttpResponse } from '@angular/common/http';
import { TranslateService } from '@ngx-translate/core';
import { Location } from '@angular/common';
import { Router } from '@angular/router';
import {LocationService} from '../common/location.service';
import {MatDialog, MatDialogRef} from '@angular/material';
import {ConfirmDialogComponent} from 'app/shared/confirm-dialog/confirm-dialog.component';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  form: FormGroup;

  wasFormSent = false;

  formValidationMessage = '';

  dialogRef: MatDialogRef<ConfirmDialogComponent>;

  constructor(private accountService: AccountService, private location: Location, private translateService: TranslateService,
              private router: Router,
              private locationService: LocationService, public dialog: MatDialog) { }

  ngOnInit() {
    this.locationService.passRouter(
      this.translateService.instant('LOCATION.YOUR_LOCATION') + ': ' +
      this.translateService.instant('LOCATION.REGISTER_PAGE'));
    this.initializeForm();
  }

  sendForm() {
    this.wasFormSent = true;

    if (this.form.valid) {

      this.dialogRef = this.dialog.open(ConfirmDialogComponent, {
        disableClose: false
      });
      this.dialogRef.componentInstance.confirmMessage = this.translateService.instant('DIALOG.ARE_YOU_SURE');

      this.dialogRef.afterClosed().subscribe(result => {
        if (result) {
          const account: AccountData = <AccountData>this.form.value;
          if (account.flatNumber === '') {
            account.flatNumber = null;
          }
          this.accountService.registerAccount(account).subscribe(() => {
              alert(this.translateService.instant('SUCCESS.REGISTER'));
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
    return (this.form.get(controlName).errors &&
    this.form.get(controlName).errors.required) &&
    ((this.form.get(controlName).dirty || this.form.get(controlName).touched) || this.wasFormSent);
  }

  private initializeForm() {
    this.form = new FormGroup({
      login: new FormControl('', [
        Validators.required
      ]),
      firstPassword: new FormControl('', [
        Validators.required
      ]),
      secondPassword: new FormControl('', [
        Validators.required
      ]),
      email: new FormControl('', [
        Validators.required
      ]),
      name: new FormControl('', [
        Validators.required
      ]),
      surname: new FormControl('', [
        Validators.required
      ]),
      phone: new FormControl('', [
        Validators.required
      ]),
      street: new FormControl('', [
        Validators.required
      ]),
      streetNumber: new FormControl('', [
        Validators.required
      ]),
      flatNumber: new FormControl(''
      ),
      postalCode: new FormControl('', [
        Validators.required
      ]),
      city: new FormControl('', [
        Validators.required
      ]),
      country: new FormControl('', [
        Validators.required
      ])
    });
  }



}

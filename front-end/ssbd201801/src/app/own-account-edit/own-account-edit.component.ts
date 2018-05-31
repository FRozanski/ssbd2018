import { Component, OnInit, ChangeDetectorRef, OnChanges, ElementRef, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { AccountService } from '../common/account.service';
import { AccountData } from '../model/account-data';
import { HttpResponse } from '@angular/common/http';
import { TranslateService } from '@ngx-translate/core';
import { Location } from '@angular/common';
import { Router, ActivatedRoute } from '@angular/router';
import {LocationService} from '../common/location.service';
import {ConfirmDialogComponent} from '../confirm-dialog/confirm-dialog.component';
import {MatDialog, MatDialogRef} from '@angular/material';

@Component({
  selector: 'app-own-account-edit',
  templateUrl: './own-account-edit.component.html',
  styleUrls: ['./own-account-edit.component.css']
})
export class OwnAccountEditComponent implements OnInit {
    form: FormGroup;
    wasFormSent: boolean = false;
    formValidationMessage: string = "";

    idEditToken: number;
    version: number;

    dialogRef: MatDialogRef<ConfirmDialogComponent>;

    constructor(
        private accountService: AccountService,
        private location: Location,
        private translateService: TranslateService,
        private router: Router,
        private activatedRoute: ActivatedRoute,
        private locationService: LocationService,
        public dialog: MatDialog
    ) { }

    ngOnInit() {
      this.locationService.passRouter(
        this.translateService.instant('LOCATION.YOUR_LOCATION') + ': ' +
        this.translateService.instant('LOCATION.OWN_ACCOUNT_EDIT_PAGE'));
      this.initializeForm();
      this.loadUserData();
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
            let account: AccountData = <AccountData>this.form.value;
            account.id = this.idEditToken;
            account.version = this.version;
            if (account.flatNumber === '') {
              account.flatNumber = null;
            }
            this.accountService.updateMyAccount(account).subscribe(() => {
                alert(this.translateService.instant('SUCCESS.ACCOUNT_EDIT'));
                this.router.navigate(['/main']);
              },
              (errorResponse) => {
                this.formValidationMessage = errorResponse.error.message;
                this.loadUserData();
              }
            )
          }
          this.dialogRef = null;
        });
      }
    }

    onReturnClick() {
      this.location.back();
    }

    isRequiredSatisfied(controlName: string) {
      return (
                this.form.get(controlName).errors &&
                this.form.get(controlName).errors.required) &&
                ((
                    this.form.get(controlName).dirty ||
                    this.form.get(controlName).touched) ||
                    this.wasFormSent
                );
    }

    private initializeForm() {
      this.form = new FormGroup({
        email: new FormControl("", [
          Validators.required
        ]),
        name: new FormControl("", [
          Validators.required
        ]),
        surname: new FormControl("", [
          Validators.required
        ]),
        phone: new FormControl("", [
          Validators.required
        ]),
        street: new FormControl("", [
          Validators.required
        ]),
        streetNumber: new FormControl("", [
          Validators.required
        ]),
        flatNumber: new FormControl(''
          ),
        postalCode: new FormControl("", [
          Validators.required
        ]),
        city: new FormControl("", [
          Validators.required
        ]),
        country: new FormControl("", [
          Validators.required
        ])
      });
    }

    private loadUserData() {
      this.accountService.getMyAccountToEdit().subscribe((account: AccountData) => {

      if(!account.flatNumber) account.flatNumber = "";

        this.form.setValue({
          "city": account.city,
          "country": account.country,
          "email": account.email,
          "name": account.name,
          "surname": account.surname,
          "phone": account.phone,
          "postalCode": account.postalCode,
          "street": account.street,
          "streetNumber": account.streetNumber,
          "flatNumber": account.flatNumber
        });

        this.idEditToken = account.id;
        this.version = account.version;

      });
    }


}

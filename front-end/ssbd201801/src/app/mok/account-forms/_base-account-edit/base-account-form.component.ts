import {Component, OnInit, Input, EventEmitter, Output} from '@angular/core';
import {Router, ActivatedRoute} from '@angular/router';
import {Location} from '@angular/common';
import {FormGroup, FormControl, Validators} from '@angular/forms';
import {AccountService} from '../../common/account.service';
import {AccountData} from '../../model/account-data';
import {Observer} from 'rxjs/Observer';
import {Observable} from 'rxjs/Observable';
import {ConfirmDialogComponent} from '../../../shared/confirm-dialog/confirm-dialog.component';
import {MatDialog, MatDialogRef} from '@angular/material';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-base-account-form',
  templateUrl: './base-account-form.component.html',
  styleUrls: ['./base-account-form.component.css']
})
export class BaseAccountFormComponent implements OnInit {

  wasFormSent = false;
  idEditToken: number;
  version: number;
  form: FormGroup;

  dialogRef: MatDialogRef<ConfirmDialogComponent>;

  @Input()
  formValidationMessage = '';

  @Input()
  accountSource: Observable<AccountData>;

  @Output('onFormSend')
  onFormSendEmitter: EventEmitter<AccountData> = new EventEmitter<AccountData>();

  @Output('onCancelClick')
  onCancelClickEmitter: EventEmitter<any> = new EventEmitter<any>();

  constructor(public dialog: MatDialog,
              private translateService: TranslateService) {
  }

  ngOnInit() {
    this.initializeForm();
    this.loadUserData();
  }

  private initializeForm() {
    this.form = new FormGroup({
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

  sendForm() {
    this.wasFormSent = true;

    if (this.form.valid) {
      this.dialogRef = this.dialog.open(ConfirmDialogComponent, {
        disableClose: false
      });

      this.dialogRef.afterClosed().subscribe(result => {
        if (result) {

          const account: AccountData = <AccountData>this.form.value;
          account.id = this.idEditToken;
          account.version = this.version;
          if (account.flatNumber === '') {
            account.flatNumber = null;
          }
          this.onFormSendEmitter.emit(this.form.value);
        }
        this.dialogRef = null;
      });
    }
  }

  loadUserData() {
    this.accountSource.subscribe((account) => {

      if (account.flatNumber == null) account.flatNumber = '';

      this.form.setValue({
        'city': account.city,
        'country': account.country,
        'email': account.email,
        'name': account.name,
        'surname': account.surname,
        'phone': account.phone,
        'postalCode': account.postalCode,
        'street': account.street,
        'streetNumber': account.streetNumber,
        'flatNumber': account.flatNumber
      });

      this.idEditToken = account.id;
      this.version = account.version;
    });
  }

  onReturnClick() {
    this.onCancelClickEmitter.emit();
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

  private provideUserDataToForm(account: AccountData) {
    if (!account.flatNumber) account.flatNumber = '';
  }

}

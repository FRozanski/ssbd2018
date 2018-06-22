import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {Location} from '@angular/common';
import {MatDialog, MatDialogRef} from '@angular/material';
import {LocationService} from '../../mok/common/location.service';
import { ConfirmDialogComponent } from '../../shared/confirm-dialog/confirm-dialog.component';
import { ShippingMethodService } from '../common/shipping-method.service';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { ShippingMethod } from '../model/shipping-method';
import { NotificationService } from '../../mok/common/notification.service';
import { SessionService } from '../../mok/common/session.service';
import { AccountData } from '../../mok/model/account-data';

@Component({
  selector: 'app-add-shipping-method',
  templateUrl: './add-shipping-method.component.html',
  styleUrls: ['./add-shipping-method.component.css']
})
export class AddShippingMethodComponent implements OnInit {

  form: FormGroup;

  wasFormSent = false;

  formValidationMessage = '';

  shippingMethod: ShippingMethod = {};

  dialogRef: MatDialogRef<ConfirmDialogComponent>;

  constructor(
    private shippingMethodService: ShippingMethodService,
    private location: Location,
    public dialog: MatDialog,
    private locationService: LocationService,
    private router: Router,
    private translateService: TranslateService,
    private notificationService: NotificationService,
    private sessionService: SessionService
  ) { }

  ngOnInit() {
    this.locationService.passRouter('LOCATION.ADD_SHIPPING_METHOD_PAGE');
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
          this.shippingMethod = <ShippingMethod>this.form.value;
          this.shippingMethod.createdByLogin = 'admin';
          this.sessionService.getMyLogin().subscribe((data: AccountData) => {
            this.shippingMethod.createdByLogin = 'client';
            this.shippingMethod.createdByLogin = data.login;
          });
          this.shippingMethod.active = true;
          this.shippingMethodService.addShippingMethod(this.shippingMethod).subscribe(() => {
            this.notificationService.displayTranslatedNotification('SUCCESS.ADD_SHIPPING_METHOD');
            this.router.navigate(['/main']);
          },
          (errorResponse) => {
            console.log(errorResponse.error.message);
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
      name: new FormControl('', [
        Validators.required
      ]),
      price: new FormControl('', [
        Validators.required
      ])
    });
  }
}

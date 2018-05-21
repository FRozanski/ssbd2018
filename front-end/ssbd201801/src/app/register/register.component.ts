import { Component, OnInit, ChangeDetectorRef, OnChanges, ElementRef, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { AccountService } from '../common/account.service';
import { AccountData } from '../model/account-data';
import { HttpResponse } from '@angular/common/http';
import { TranslateService } from '@ngx-translate/core';
import { Location } from '@angular/common';
import { Router } from '@angular/router';


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  form: FormGroup;

  wasFormSent: boolean = false;

  formValidationMessage: string = "";

  constructor(private accountService: AccountService, private location: Location, private translateService: TranslateService) { }

  ngOnInit() {
    this.initializeForm();
  }

  sendForm() {   

    this.wasFormSent = true;

    if (this.form.valid) {
      let account: AccountData = <AccountData>this.form.value;

      this.accountService.registerAccount(account).subscribe(null, 
        (errorResponse) => {
          console.log("Getting translation for key: ", errorResponse.error.message);
          this.formValidationMessage = this.translateService.instant(errorResponse.error.message);
      })

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
      login: new FormControl("", [
        Validators.required
      ]),
      password: new FormControl("", [
        Validators.required
      ]),
      password2: new FormControl("", [
        Validators.required
      ]),
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
      flatNumber: new FormControl("", [
        Validators.required
      ]),
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



}

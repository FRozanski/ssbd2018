import { Component, OnInit, ChangeDetectorRef, OnChanges, ElementRef, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { AccountService } from '../common/account.service';
import { AccountData } from '../model/account-data';
import { HttpResponse } from '@angular/common/http';
import { TranslateService } from '@ngx-translate/core';
import { Location } from '@angular/common';
import { Router, ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-account-edit',
  templateUrl: './account-edit.component.html',
  styleUrls: ['./account-edit.component.css']
})
export class AccountEditComponent implements OnInit {

  form: FormGroup;
  userId: number;
  wasFormSent: boolean = false;
  formValidationMessage: string = "";

  idEditToken: string;

  constructor(private accountService: AccountService, private location: Location, private translateService: TranslateService, private router: Router, private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.initializeForm();
    this.userId = this.activatedRoute.snapshot.params['id'];
    this.loadUserData();
  }

  sendForm() {
    this.wasFormSent = true;

    if (this.form.valid) {
      let account: AccountData = <AccountData>this.form.value;
      account.id = this.idEditToken;
      this.accountService.editAccount(account).subscribe(() => {
        alert("Pomyślnie edytowano użytkownika"); // temp
        this.router.navigate(['/main'])
      },
        (errorResponse) => {
          this.formValidationMessage = errorResponse.error.message;          
        }
      )
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

  private loadUserData() {
    this.accountService.getAccountToEdit(this.userId).subscribe((account: AccountData) => {
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
        "flatNumber": account.flatNumber + ""
      });

      this.idEditToken = account.id;

    });
  }



}

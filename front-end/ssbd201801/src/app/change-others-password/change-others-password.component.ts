import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {AccountData} from '../model/account-data';
import {AccountService} from '../common/account.service';
import {Router} from '@angular/router';
import {Location} from '@angular/common';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-change-others-password',
  templateUrl: './change-others-password.component.html',
  styleUrls: ['./change-others-password.component.css']
})
export class ChangeOthersPasswordComponent implements OnInit {

  form: FormGroup;

  wasFormSent = false;

  formValidationMessage = '';

  othersLogin = '';

  constructor(private accountService: AccountService, private location: Location, private translateService: TranslateService, private router: Router) { }

  ngOnInit() {
    this.accountService.currentLogin.subscribe(login => this.othersLogin = login);
    this.initializeForm();
  }

  sendForm() {
    this.wasFormSent = true;

    if (this.form.valid) {
      const account: AccountData = <AccountData>this.form.value;
      account.login = this.othersLogin;
      this.accountService.changeOthersPassword(account).subscribe(() => {
          this.router.navigate(['/main']);
        },
        (errorResponse) => {
          this.formValidationMessage = this.translateService.instant(errorResponse.error.message);
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
      newPassOne: new FormControl('', [
        Validators.required
      ]),
      newPassTwo: new FormControl('', [
        Validators.required
      ])
    });
  }

}

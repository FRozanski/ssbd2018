import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {AccountService} from '../common/account.service';
import {Router} from '@angular/router';
import {Location} from '@angular/common';
import {TranslateService} from '@ngx-translate/core';
import {AccountData} from '../model/account-data';
import {SessionService} from '../common/session.service';

@Component({
  selector: 'app-change-password',
  templateUrl: './change-password.component.html',
  styleUrls: ['./change-password.component.css']
})
export class ChangePasswordComponent implements OnInit {

  form: FormGroup;

  wasFormSent: boolean = false;

  formValidationMessage: string = "";

  userIdentity: AccountData = {};

  myAccountToEdit: AccountData = {};

  constructor(private accountService: AccountService, private sessionService: SessionService, private location: Location, private translateService: TranslateService, private router: Router) { }

  ngOnInit() {

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
      let account: AccountData = <AccountData>this.form.value;
      account.id = this.myAccountToEdit.id;
      account.version = this.myAccountToEdit.version;
      this.accountService.changeMyPassword(account).subscribe(() => {
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
      oldPass: new FormControl("", [
        Validators.required
      ]),
      newPassOne: new FormControl("", [
        Validators.required
      ]),
      newPassTwo: new FormControl("", [
        Validators.required
      ])
    });
  }
}

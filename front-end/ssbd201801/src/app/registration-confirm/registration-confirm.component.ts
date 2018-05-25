import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import 'rxjs/add/operator/filter';
import {RegistrationConfirmService} from '../common/registration-confirm.service';
import {AccountData} from '../model/account-data';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-registration-confirm',
  templateUrl: './registration-confirm.component.html',
  styleUrls: ['./registration-confirm.component.css']
})
export class RegistrationConfirmComponent implements OnInit {
  token: string;
  isConfirm = false;

  validationMessage = '';

  constructor (
    private registrationConfirmService: RegistrationConfirmService,
    private route: ActivatedRoute,
    private translateService: TranslateService
  ) { }

  ngOnInit() {
    this.route.queryParams
      .filter(params => params.token)
      .subscribe(params => this.token = params.token);

    this.registrationConfirmService.confirmAccount(this.token)
      .subscribe((data: AccountData) => {
        // this.accountToConfirm = data;
        this.isConfirm = data.confirm;
      }, (errorResponse) => {
        console.log(errorResponse.error.message);
        // this.validationMessage = this.translateService.instant(errorResponse.error.message);
      });
  }

}

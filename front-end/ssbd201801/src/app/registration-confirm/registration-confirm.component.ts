import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import 'rxjs/add/operator/filter';
import {AccountData} from '../model/account-data';
import {TranslateService} from '@ngx-translate/core';
import {AccountService} from '../common/account.service';

@Component({
  selector: 'app-registration-confirm',
  templateUrl: './registration-confirm.component.html',
  styleUrls: ['./registration-confirm.component.css']
})
export class RegistrationConfirmComponent implements OnInit {
  token: string;
  validationMessage = '';

  constructor (
    private accountService: AccountService,
    private route: ActivatedRoute,
    private translateService: TranslateService
  ) { }

  ngOnInit() {
    this.route.queryParams
      .filter(params => params.token)
      .subscribe(params => this.token = params.token);

    this.accountService.confirmAccountByToken(this.token)
      .subscribe((data: AccountData) => {
        alert(this.translateService.instant('REGISTER.ACCOUNT_CONFIRMED'));
      }, (errorResponse) => {
        this.validationMessage = this.translateService.instant(errorResponse.error.message);
      });
  }

}

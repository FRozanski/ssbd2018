import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import 'rxjs/add/operator/filter';
import {RegistrationConfirmService} from '../common/registration-confirm.service';
import {AccountData} from '../model/account-data';

@Component({
  selector: 'app-registration-confirm',
  templateUrl: './registration-confirm.component.html',
  styleUrls: ['./registration-confirm.component.css']
})
export class RegistrationConfirmComponent implements OnInit {
  token: string;
  accountToConfirm: AccountData = {};
  isConfirm = 'nie działa';

  constructor (
    private registrationConfirmService: RegistrationConfirmService,
    private route: ActivatedRoute
  ) { }

  ngOnInit() {
    this.route.queryParams
      .filter(params => params.token)
      .subscribe(params => this.token = params.token);

    this.registrationConfirmService.confirmAccount(this.token)
      .subscribe((data: AccountData) => {
        this.accountToConfirm = data;
        this.isConfirm = this.accountToConfirm.login;
      }, () => {
        this.isConfirm = 'no nie';
      });

  }

}

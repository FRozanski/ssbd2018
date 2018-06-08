import { Component, OnInit, ChangeDetectorRef, OnChanges, ElementRef, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { AccountService } from '../../common/account.service';
import { AccountData } from '../../model/account-data';
import { HttpResponse } from '@angular/common/http';
import { TranslateService } from '@ngx-translate/core';
import { Location } from '@angular/common';
import { Router, ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import { NotificationService } from '../../common/notification.service';
import {LocationService} from '../../common/location.service';
import {BaseAccountFormComponent} from '../_base-account-edit/base-account-form.component';

@Component({
  selector: 'app-account-edit',
  templateUrl: './account-edit.component.html',
  styleUrls: ['./account-edit.component.css']
})
export class AccountEditComponent implements OnInit {

  accountSource: Observable<AccountData>;
  userId: number;
  formValidationMessage = '';

  constructor(
    private accountService: AccountService,
    private translateService: TranslateService,
    private router: Router,
    private location: Location,
    private locationService: LocationService,
    private notificationService: NotificationService,
    private activatedRoute: ActivatedRoute
  ) {
    this.userId = this.activatedRoute.snapshot.params["id"];
    this.loadUserData();
  }

  ngOnInit() {
    this.locationService.passRouter(
      this.translateService.instant('LOCATION.YOUR_LOCATION') + ': ' +
      this.translateService.instant('LOCATION.ACCOUNT_LIST_PAGE') +
      ' ' + String.fromCharCode(0x2192) + ' ' +
      this.translateService.instant('LOCATION.ACCOUNT_EDIT_PAGE'));
  }

  onFormSend(account: AccountData) {
    this.accountService.updateAccount(account).subscribe(() => {
      this.notificationService.displayTranslatedNotification('SUCCESS.ACCOUNT_EDIT');
      this.router.navigate(['/main']);
    },
      (errorResponse) => {
        this.formValidationMessage = this.translateService.instant(errorResponse.error.message);
        // this.notificationService.displayTranslatedNotification(errorResponse.error.message);
        this.loadUserData();
      }
    )
  }

  onCancelClick() {
    this.location.back();
  }

  private loadUserData() {
    this.accountSource = this.accountService.getAccountToEdit(this.userId);

  }

}

import { Component, OnInit, ChangeDetectorRef, OnChanges, ElementRef, ViewChild } from '@angular/core';
import { FormGroup, FormBuilder, FormControl, Validators } from '@angular/forms';
import { HttpResponse } from '@angular/common/http';
import { TranslateService } from '@ngx-translate/core';
import { Location } from '@angular/common';
import { Router, ActivatedRoute } from '@angular/router';
import { AccountService } from '../../common/account.service';
import { AccountData } from '../../model/account-data';
import { Observable } from 'rxjs/Observable';
import { NotificationService } from '../../common/notification.service';
import {LocationService} from '../../common/location.service';
import {BaseAccountFormComponent} from '../_base-account-edit/base-account-form.component';

@Component({
  selector: 'app-own-account-edit',
  templateUrl: './own-account-edit.component.html',
  styleUrls: ['./own-account-edit.component.css']
})
export class OwnAccountEditComponent implements OnInit {

  accountSource: Observable<AccountData>;
  formValidationMessage = '';

  constructor(
    private accountService: AccountService,
    private translateService: TranslateService,
    private router: Router,
    private location: Location,
    private locationService: LocationService,
    private notificationService: NotificationService
  ) { }

  ngOnInit() {
    this.locationService.passRouter(
      this.translateService.instant('LOCATION.YOUR_LOCATION') + ': ' +
      this.translateService.instant('LOCATION.OWN_ACCOUNT_EDIT_PAGE'));
    this.loadUserData();
  }

  onFormSend(account: AccountData) {
    this.accountService.updateMyAccount(account).subscribe(() => {
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
    this.accountSource = this.accountService.getMyAccountToEdit();
  }

}

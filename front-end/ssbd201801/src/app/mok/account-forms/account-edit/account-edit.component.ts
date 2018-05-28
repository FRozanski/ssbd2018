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

@Component({
  selector: 'app-account-edit',
  templateUrl: './account-edit.component.html',
  styleUrls: ['./account-edit.component.css']
})
export class AccountEditComponent {

  accountSource: Observable<AccountData>;
  formValidationMessage: string;
  userId: number;

  constructor(
    private accountService: AccountService,
    private translateService: TranslateService,
    private router: Router,
    private location: Location,
    private notificationService: NotificationService,
    private activatedRoute: ActivatedRoute
  ) { 
    this.userId = this.activatedRoute.snapshot.params["id"];
    this.loadUserData();
  }

  ngOnInit() {
  }

  onFormSend(account: AccountData) {
    this.accountService.updateAccount(account).subscribe(() => {
      this.notificationService.displayNotification(this.translateService.instant('SUCCESS.ACCOUNT_EDIT'));
      this.router.navigate(['/main']);
    },
      (errorResponse) => {
        this.notificationService.displayNotification(this.translateService.instant(errorResponse.error.message));
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

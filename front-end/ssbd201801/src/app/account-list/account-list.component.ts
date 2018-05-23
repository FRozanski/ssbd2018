
import {Component, OnInit, ChangeDetectorRef} from '@angular/core';
import { AccountService } from '../common/account.service';
import { HttpErrorResponse } from '@angular/common/http';
import { AccountData } from '../model/account-data';
import { environment } from '../../environments/environment'
import { Router } from '@angular/router';

@Component({
  selector: 'app-account-list',
  templateUrl: './account-list.component.html',
  styleUrls: ['./account-list.component.css']
})
export class AccountListComponent implements OnInit {

  displayedColumns = [
    'city', 'confirm', 'country', 'email', 'firstName',
    'lastName', 'login', 'numberOfLogins', 'numberOfOrders',
    'numberOfProducts', 'phone', 'postalCode', 'street', 'streetNumber', 'edit', 'changePassword'
  ];
  dataSource;

  constructor (private accountService: AccountService, private router: Router) { }

  ngOnInit() {
    this.accountService.getAllAccounts().subscribe((data) => {
      this.dataSource = data;
    });
  }

  onEditClick(account: AccountData) {
    this.router.navigate(["/accountEdit/" + account.id]);
  }


  onChangePasswordClick(account: AccountData) {
    this.accountService.passLogin(account.login);
    // window.location.href = environment.apiUrl + '/ng/#/changeOthersPassword';
    this.router.navigate(["/changeOthersPassword/" + account.id]);

  }
}

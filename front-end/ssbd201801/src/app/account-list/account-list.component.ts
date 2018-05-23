
import {Component, OnInit, ChangeDetectorRef} from '@angular/core';
import { AccountService } from '../common/account.service';
import { HttpErrorResponse } from '@angular/common/http';
import { AccountData } from '../model/account-data';
import { environment } from '../../environments/environment';

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

  constructor (private accountService: AccountService) { }

  ngOnInit() {
    this.accountService.getAllAccounts().subscribe((data) => {
      this.dataSource = data;
    });
  }

  onEditClick(account: AccountData) {
    throw new Error('onEditClick(..) is not implemented yet.');
  }


  onChangePasswordClick(account: AccountData) {
    this.accountService.passLogin(account.login);
    window.location.href = environment.apiUrl + '/ng/#/changeOthersPassword';
  }
}

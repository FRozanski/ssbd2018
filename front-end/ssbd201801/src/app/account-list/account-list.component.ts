
import {Component, OnInit, ViewChild} from '@angular/core';
import { AccountService } from '../common/account.service';
import { HttpErrorResponse } from '@angular/common/http';
import { AccountData } from '../model/account-data';
import { environment } from '../../environments/environment';
import {MatTableDataSource, MatSort} from '@angular/material';
@Component({
  selector: 'app-account-list',
  templateUrl: './account-list.component.html',
  styleUrls: ['./account-list.component.css']
})
export class AccountListComponent implements OnInit {

  displayedColumns = [
    'city', 'confirm', 'country', 'email', 'firstName',
    'lastName', 'login', 'numberOfLogins', 'numberOfOrders',
    'numberOfProducts', 'phone', 'postalCode', 'street', 'streetNumber', 'edit'
  ];
  dataSource;

  constructor (private accountService: AccountService) { }

  @ViewChild(MatSort) sort: MatSort;

  ngOnInit() {
    this.accountService.getAllAccounts().subscribe((data) => {
      this.dataSource = new MatTableDataSource(data);
    }, () => {
      window.location.href = environment.apiUrl + '/login/login.html';
    });
    this.dataSource.sort = this.sort;
  }

  onEditClick(account: AccountData) {
    throw new Error('onEditClick(..) is not implemented yet.');
  }

}

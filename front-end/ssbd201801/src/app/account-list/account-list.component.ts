
import {Component, OnInit, ViewChild} from '@angular/core';
import { AccountService } from '../common/account.service';
import { HttpErrorResponse } from '@angular/common/http';
import { AccountData } from '../model/account-data';
import { environment } from '../../environments/environment';
import {MatTableDataSource, MatSort, MatPaginator} from '@angular/material';
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
  @ViewChild(MatPaginator) paginator: MatPaginator;

  ngOnInit() {
    this.accountService.getAllAccounts().subscribe((data) => {
      this.dataSource = new MatTableDataSource<AccountData>(data);
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
    }, () => {
      window.location.href = environment.apiUrl + '/login/login.html';
    });
  }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // MatTableDataSource defaults to lowercase matches
    this.dataSource.filter = filterValue;
  }

  onEditClick(account: AccountData) {
    throw new Error('onEditClick(..) is not implemented yet.');
  }

}

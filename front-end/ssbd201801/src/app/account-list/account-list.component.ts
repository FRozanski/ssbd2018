
import {Component, OnInit, ViewChild, ChangeDetectorRef} from '@angular/core';
import { AccountService } from '../common/account.service';
import { HttpErrorResponse } from '@angular/common/http';
import { AccountData } from '../model/account-data';
import { environment } from '../../environments/environment'
import { Router } from '@angular/router';
import {MatTableDataSource, MatSort, MatPaginator} from '@angular/material';

@Component({
  selector: 'app-account-list',
  templateUrl: './account-list.component.html',
  styleUrls: ['./account-list.component.css']
})
export class AccountListComponent implements OnInit {

  displayedColumns = [
    'login', 'firstName', 'surname', 'email', 'phone', 'country', 'city', 'street',
    'streetNumber', 'flatNumber', 'postalCode', 'edit', 'changePassword'
    ];
  dataSource;

  constructor (private accountService: AccountService, private router: Router) { }

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  ngOnInit() {
    this.accountService.getAllAccounts().subscribe((data) => {
      this.dataSource = new MatTableDataSource<AccountData>(data);
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
    });
  }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); 
    filterValue = filterValue.toLowerCase(); 
    this.dataSource.filter = filterValue;
  }

  onEditClick(account: AccountData) {
    this.router.navigate(["/accountEdit/" + account.id]);
  }


  onChangePasswordClick(account: AccountData) {
    this.accountService.passId(+account.id);
    this.router.navigate(["/changeOthersPassword"]);
  }
}

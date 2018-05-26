import {Component, OnInit, ViewChild} from '@angular/core';
import {MatPaginator, MatSort, MatTableDataSource} from '@angular/material';
import {Router} from '@angular/router';
import {AccountService} from '../common/account.service';
import {environment} from '../../environments/environment';
import {AccountData} from '../model/account-data';
import {TranslateService} from '@ngx-translate/core';

@Component({
  selector: 'app-account-statistics',
  templateUrl: './account-statistics.component.html',
  styleUrls: ['./account-statistics.component.css']
})
export class AccountStatisticsComponent implements OnInit {

  displayedColumns = [
    'login', 'confirm', 'active',
    'numberOfLogins', 'numberOfOrders', 
    'numberOfProducts', 'confirmAccount'
    ];
  dataSource;

  validationMessage = '';

  constructor (private accountService: AccountService, private router: Router,
               private translateService: TranslateService) { }

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
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // MatTableDataSource defaults to lowercase matches
    this.dataSource.filter = filterValue;
  }

  onConfirmClick(account: AccountData) {
    this.accountService.confirmAccount(account.id).subscribe(() => {
      alert(this.translateService.instant('success'));
    },
      (errorResponse) => {
        this.validationMessage = this.translateService.instant(errorResponse.error.message);
      });
  }

  isConfirm(account: AccountData) {
    if (account.confirm) { return true; }
    else { return false; }
  }

}

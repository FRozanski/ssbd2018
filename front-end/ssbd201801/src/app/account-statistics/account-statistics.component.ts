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
    , 'adminAccessLevel', 'managerAccessLevel', 'userAccessLevel'
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
      window.location.reload();
    },
      (errorResponse) => {
        this.validationMessage = this.translateService.instant(errorResponse.error.message);
      });
  }

  onLockUnlockClick(account: AccountData) {
    if (this.isActive(account)) {
      this.accountService.lockAccount(account.id).subscribe(() => {
          alert(this.translateService.instant('success'));
          window.location.reload();
        },
        (errorResponse) => {
          this.validationMessage = this.translateService.instant(errorResponse.error.message);
        });
    } else {
      this.accountService.unlockAccount(account.id).subscribe(() => {
          alert(this.translateService.instant('success'));
          window.location.reload();
        },
        (errorResponse) => {
          this.validationMessage = this.translateService.instant(errorResponse.error.message);
        });
    }
  }

  onAddDeleteAdminClick(account: AccountData) {
    if (!this.isAdmin(account)) {
      account.accessLevels.push('admin');
    } else {
      account.accessLevels.splice(this.adminId(account), 1);
    }
    this.alterAccountAccessLevel(account);
  }

  onAddDeleteManagerClick(account: AccountData) {
    if (!this.isManager(account)) {
      account.accessLevels.push('manager');
    } else {
      account.accessLevels.splice(this.managerId(account), 1);
    }
    this.alterAccountAccessLevel(account);
  }

  onAddDeleteUserClick(account: AccountData) {
    if (!this.isUser(account)) {
      account.accessLevels.push('user');
    } else {
      account.accessLevels.splice(this.userId(account), 1);
    }
    this.alterAccountAccessLevel(account);
  }

  private alterAccountAccessLevel(account: AccountData) {
    this.accountService.alterAccountAccessLevel(account).subscribe(() => {
        alert(this.translateService.instant('success'));
      },
      (errorResponse) => {
        alert(this.translateService.instant(errorResponse.error.message));
        window.location.reload();
      });
  }

  isConfirm(account: AccountData) {
    if (account.confirm) { return true; }
    else { return false; }
  }

  isActive(account: AccountData) {
    if (account.active) { return true; }
    else { return false; }
  }

  isAdmin(account: AccountData) {
    for (const level of account.accessLevels) {
      if (level === 'admin') {
        return true;
      }
    }
    return false;
  }

  adminId(account: AccountData) {
    let i = 0;
    for (const level of account.accessLevels) {
      if (level === 'admin') {
        return i;
      }
      i++;
    }
    return null;
  }

  managerId(account: AccountData) {
    let i = 0;
    for (const level of account.accessLevels) {
      if (level === 'manager') {
        return i;
      }
      i++;
    }
    return null;
  }

  userId(account: AccountData) {
    let i = 0;
    for (const level of account.accessLevels) {
      if (level === 'user') {
        return i;
      }
      i++;
    }
    return null;
  }

  isManager(account: AccountData) {
    for (const level of account.accessLevels) {
      if (level === 'manager') {
        return true;
      }
    }
    return false;
  }

  isUser(account: AccountData) {
    for (const level of account.accessLevels) {
      if (level === 'user') {
        return true;
      }
    }
    return false;
  }

  applyAddOrDeleteAdminAccessLevelLabel(account: AccountData) {
    if (this.isAdmin(account))
      return this.translateService.instant('ACCOUNT.DELETE_ACCESS_LEVEL');
    else
      return this.translateService.instant('ACCOUNT.ADD_ACCESS_LEVEL');
  }

  applyAddOrDeleteManagerAccessLevelLabel(account: AccountData) {
    if (this.isManager(account))
      return this.translateService.instant('ACCOUNT.DELETE_ACCESS_LEVEL');
    else
      return this.translateService.instant('ACCOUNT.ADD_ACCESS_LEVEL');
  }

  applyAddOrDeleteUserAccessLevelLabel(account: AccountData) {
    if (this.isUser(account))
      return this.translateService.instant('ACCOUNT.DELETE_ACCESS_LEVEL');
    else
      return this.translateService.instant('ACCOUNT.ADD_ACCESS_LEVEL');
  }

  applyLockOrUnlockAccountLabel(account: AccountData) {
    if (this.isActive(account))
      return this.translateService.instant('ACCOUNT.LOCK_ACCOUNT');
    else
      return this.translateService.instant('ACCOUNT.UNLOCK_ACCOUNT');
  }
}

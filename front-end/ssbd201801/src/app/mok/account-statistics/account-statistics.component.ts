import {Component, OnInit, ViewChild} from '@angular/core';
import {MatDialog, MatDialogRef, MatPaginator, MatSort, MatTableDataSource} from '@angular/material';
import {Router} from '@angular/router';
import {AccountService} from '../common/account.service';
import {environment} from '../../../environments/environment';
import {AccountData} from '../model/account-data';
import {TranslateService} from '@ngx-translate/core';
import {AuthUtilService} from '../common/auth-util.service';
import {SessionService} from '../common/session.service';
import {LocationService} from '../common/location.service';
import { FormControl } from '@angular/forms';
import { SelectValues, SelectValue } from '../common/mat-table-utils/select-values';
import { NotificationService } from '../common/notification.service';
import { HttpErrorResponse } from '@angular/common/http';
import { ConfirmDialogComponent } from '../../shared/confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-account-statistics',
  templateUrl: './account-statistics.component.html',
  styleUrls: ['./account-statistics.component.css']
})
export class AccountStatisticsComponent implements OnInit {

  displayedColumns = [
    {def: 'login', showManager: true},
    {def: 'lastLoginDate', showManager: true},
    {def: 'lastLoginIp', showManager: true},
    {def: 'numberOfLogins', showManager: true},
    {def: 'numberOfOrders', showManager: true},
    {def: 'numberOfProducts', showManager: true},
    {def: 'confirmAccount', showManager: true},
    {def: 'lockOrUnlockAccount', showManager: true},
    {def: 'accessLevel', showManager: true}
  ];
  dataSource;

  validationMessage = '';
  userIdentity: AccountData = {};
  rolesStringified = '';

  dialogRef: MatDialogRef<ConfirmDialogComponent>;
  changedAccounts: Set<AccountData> = new Set<AccountData>();
  changedRows: Set<number> = new Set<number>();

  readonly avaliableRoles: SelectValue[] = SelectValues.roleSelectValues;


  selectValue: any;

  constructor (private accountService: AccountService,
               private translateService: TranslateService,
               private router: Router,
               private authUtil: AuthUtilService,
               private locationService: LocationService,
               private sessionService: SessionService,
               public dialog: MatDialog,
               private notificationService: NotificationService
              ) { }

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  ngOnInit() {
    this.locationService.passRouter(
      this.translateService.instant('LOCATION.YOUR_LOCATION') + ': ' +
      this.translateService.instant('LOCATION.ACCOUNT_STATISTICS_PAGE'));
    this.accountService.getAllAccounts().subscribe((data) => {
      this.dataSource = new MatTableDataSource<AccountData>(data);
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;
    });
    this.updateRoles(); 

  }

  onAccountChange(account: AccountData, rowId: number)
  {
    this.changedAccounts.add(account);
    this.changedRows.add(rowId);
  }


  onConfirmClick(account: AccountData) {
    this.dialogRef = this.dialog.open(ConfirmDialogComponent, {
      disableClose: false
    });
    this.dialogRef.componentInstance.confirmMessage = this.translateService.instant('DIALOG.ARE_YOU_SURE');

    this.dialogRef.afterClosed().subscribe(result => {
      if (result) {
        this.accountService.confirmAccount(account.id).subscribe(() => {
            alert(this.translateService.instant('SUCCESS.ACCOUNT_CONFIRM'));
            window.location.reload();
          },
          (errorResponse) => {
            this.validationMessage = this.translateService.instant(errorResponse.error.message);
          });
      }
      this.dialogRef = null;
    });
  }

  submitAccounts() {
    for (let i=0; i<this.changedAccounts.size; i++) {
      this.alterAccountAccessLevel(this.changedAccounts[i])
    }
  }

  private alterAccountAccessLevel(account: AccountData) {
    this.accountService.alterAccountAccessLevel(account).subscribe(() => {
        alert(this.translateService.instant('SUCCESS.ALTER_ACCOUNT_ACCESS_LEVEL'));
      },
      (errorResponse) => {
        alert(this.translateService.instant(errorResponse.error.message));
        window.location.reload();
      });
  }

  wasRowChanged(rowId) {
    return this.changedRows.has(rowId);
  }

  getDisplayedColumns(): string[] {
    const isManager = this.hasRole('MANAGER') && !this.hasRole('ADMIN');
    return this.displayedColumns
      .filter(cd => !isManager || cd.showManager)
      .map(cd => cd.def);
  }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // MatTableDataSource defaults to lowercase matches
    this.dataSource.filter = filterValue;
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

  updateRoles() {
    this.sessionService.getMyIdentity().toPromise().then((data) => {
      this.userIdentity.roles = data.roles;
      this.rolesStringified = JSON.stringify(data.roles);
    }).catch((data) => {
      this.userIdentity.roles = this.translateService.instant('GUEST');
      this.rolesStringified = JSON.stringify(this.userIdentity.roles);
    });
  }

  hasRole(role: string): boolean {
    return this.authUtil.hasRole(role, this.userIdentity);
  }
}

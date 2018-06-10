import {Component, OnInit, ViewChild, ChangeDetectorRef} from '@angular/core';
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
import { Subscription } from 'rxjs/Subscription';
import { Observable } from 'rxjs/Observable';

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
  userIdentity: AccountData = {};
  rolesStringified = '';
  filterValue: string = '';
  dialogRef: MatDialogRef<ConfirmDialogComponent>;
  changedAccounts: Set<AccountData> = new Set<AccountData>();
  accountsConfirmedAndNotSent: Set<number> = new Set<number>();
  changedRows: Set<number> = new Set<number>();
  private numberOfEditResponses: number = 0;

  private successfullySentAccounts: Set<AccountData> = new Set<AccountData>();
  private faultlySentAccounts: Set<AccountData> = new Set<AccountData>();


  registerResponse() {    
    this.numberOfEditResponses++;
    if(this.numberOfEditResponses*3 >= this.changedAccounts.size) {
      let message: string = this.translateService.instant('SUCCESS.UPDATED_ACCOUNTS');
      this.successfullySentAccounts.forEach((account: AccountData) => {
        message += account.login + ", ";
      });
      
      if(this.faultlySentAccounts.size !== 0) message += this.translateService.instant('ERROR.FAILED_ACCOUNTS_UPDATE');
      this.faultlySentAccounts.forEach((account: AccountData) => {
        message += account.login + ", ";
      })

      this.notificationService.displayTranslatedNotification(message);
      this.changedAccounts.clear();
      this.changedRows.clear();
      this.accountsConfirmedAndNotSent.clear();
    }
  }

  private submitStatusMessage: string = '';

  readonly avaliableRoles: SelectValue[] = SelectValues.roleSelectValues;


  selectValue: any;

  constructor (private accountService: AccountService,
               private translateService: TranslateService,
               private router: Router,
               private authUtil: AuthUtilService,
               private locationService: LocationService,
               private sessionService: SessionService,
               public dialog: MatDialog,
               private notificationService: NotificationService,
               private changeDetectorRef: ChangeDetectorRef
              ) { }

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  ngOnInit() {
    this.locationService.passRouter('LOCATION.ACCOUNT_STATISTICS_PAGE');
    this.accountService.getAllAccounts().subscribe((data) => {
      this.dataSource = new MatTableDataSource<AccountData>(data);
      this.dataSource.sort = this.sort;
      this.dataSource.paginator = this.paginator;

      this.translateService.get('app.pagination.itemsPerPageLabel').subscribe(translation => {
        this.paginator._intl.itemsPerPageLabel = translation;
      });
      this.translateService.get('app.pagination.firstPageLabel').subscribe(translation => {
        this.paginator._intl.firstPageLabel = translation;
      });
      this.translateService.get('app.pagination.previousPageLabel').subscribe(translation => {
        this.paginator._intl.previousPageLabel = translation;
      });
      this.translateService.get('app.pagination.nextPageLabel').subscribe(translation => {
        this.paginator._intl.nextPageLabel = translation;
      });
      this.translateService.get('app.pagination.lastPageLabel').subscribe(translation => {
        this.paginator._intl.lastPageLabel = translation;
      });
    });
    this.updateRoles(); 

  }

  onAccountConfirmChange(account: AccountData, rowId: number) {

    if(account.confirm) {
      this.accountsConfirmedAndNotSent.add(account.id);
      this.onAccountChange(account, rowId);
    }
    else {
      this.accountsConfirmedAndNotSent.delete(account.id);
      this.changedAccounts.delete(account);
      this.changedRows.delete(rowId);
    }
  }

  onAccountChange(account: AccountData, rowId: number)
  { 
    this.changedAccounts.add(account);
    this.changedRows.add(rowId); 
  }

  submitAccounts() {
    this.changedAccounts.forEach((account: AccountData) => {
      this.alterAccountAccessLevel(account);
      this.changeUserActivationStatus(account);
      if(this.accountsConfirmedAndNotSent.has(account.id)) {
        this.changeUserConfirmationStatus(account);
      }
    })

    this.changedAccounts.clear();
    this.changedRows.clear();
    this.accountsConfirmedAndNotSent.clear();

    this.router.navigate(['/statistics']);
  }

  private alterAccountAccessLevel(account: AccountData) {
    this.accountService.alterAccountAccessLevel(account).subscribe(() => {
        this.successfullySentAccounts.add(account);
        this.registerResponse();
      },
      (error: HttpErrorResponse) => {
        this.faultlySentAccounts.add(account);
        this.registerResponse();
      });
  }

  private changeUserConfirmationStatus(account: AccountData) {
    this.accountService.confirmAccount(account.id).subscribe(() => {
      this.successfullySentAccounts.add(account);
      this.registerResponse();
    }, (error: HttpErrorResponse) => {
      this.faultlySentAccounts.add(account);
      this.registerResponse();
    })
  }

  private changeUserActivationStatus(account: AccountData) {

    const isAccountLocked: boolean = !account.active;
    let observable: Observable<any> = null;

    if(isAccountLocked) observable = this.accountService.unlockAccount(account.id);
    else observable = this.accountService.lockAccount(account.id);

    observable.subscribe(() => {
      this.successfullySentAccounts.add(account);
      this.registerResponse();
    }, (error: HttpErrorResponse) => {
      this.faultlySentAccounts.add(account);
      this.registerResponse();
    })
  }

  wasRowChanged(rowId) {
    return this.changedRows.has(rowId);
  }

  wasSomeUserChanged() {
    return (this.changedAccounts.size !== 0 || this.changedRows.size != 0);
  }

  getDisplayedColumns(): string[] {
    const isManager = this.hasRole('MANAGER') && !this.hasRole('ADMIN');
    return this.displayedColumns
      .filter(cd => !isManager || cd.showManager)
      .map(cd => cd.def);
  }

  onFilterCriteriaChange(filterValue) {  

    if(this.changedAccounts.size !== 0 || this.changedRows.size != 0) {
      
      this.dialogRef = this.dialog.open(ConfirmDialogComponent, {
        disableClose: false
      });

      this.dialogRef.afterClosed().subscribe((userConfirmedOperation: boolean) => {
        if(userConfirmedOperation) {
          this.applyFilter(filterValue);
          this.changedAccounts.clear();
          this.changedRows.clear();
        } else {
          this.filterValue = '';
        }
      })

    } else {
      this.applyFilter(filterValue);
    }
  }

  onPaginatorPageChange() {
    if(this.changedAccounts.size !== 0 || this.changedRows.size != 0) {
      this.changedAccounts.clear();
      this.changedRows.clear();
      this.notificationService.displayTranslatedNotification("ACCOUNT.RESET_DATA_INFO");      
    }
  }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim(); // Remove whitespace
    filterValue = filterValue.toLowerCase(); // MatTableDataSource defaults to lowercase matches
    this.dataSource.filter = filterValue;
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

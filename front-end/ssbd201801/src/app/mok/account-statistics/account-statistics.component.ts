import { Component, OnInit, ViewChild, ChangeDetectorRef, AfterViewChecked } from '@angular/core';
import { MatDialog, MatDialogRef, MatPaginator, MatSort, MatTableDataSource, MatTab } from '@angular/material';
import { Router } from '@angular/router';
import { AccountService } from '../common/account.service';
import { environment } from '../../../environments/environment';
import { AccountData } from '../model/account-data';
import { TranslateService } from '@ngx-translate/core';
import { AuthUtilService } from '../common/auth-util.service';
import { SessionService } from '../common/session.service';
import { LocationService } from '../common/location.service';
import { FormControl } from '@angular/forms';
import { SelectValues, SelectValue } from '../common/mat-table-utils/select-values';
import { NotificationService } from '../common/notification.service';
import { HttpErrorResponse } from '@angular/common/http';
import { ConfirmDialogComponent } from '../../shared/confirm-dialog/confirm-dialog.component';
import { Subscription } from 'rxjs/Subscription';
import { Observable } from 'rxjs/Observable';
import { DataSource } from '@angular/cdk/table';

@Component({
  selector: 'app-account-statistics',
  templateUrl: './account-statistics.component.html',
  styleUrls: ['./account-statistics.component.css']
})
export class AccountStatisticsComponent implements OnInit, AfterViewChecked {

  displayedColumns = [
    { def: 'login', showManager: true },
    { def: 'lastLoginDate', showManager: true },
    { def: 'lastLoginIp', showManager: true },
    { def: 'numberOfLogins', showManager: true },
    { def: 'numberOfOrders', showManager: true },
    { def: 'numberOfProducts', showManager: true },
    { def: 'confirmAccount', showManager: true },
    { def: 'lockOrUnlockAccount', showManager: true },
    { def: 'accessLevel', showManager: true }
  ];

  dataSource: MatTableDataSource<AccountData>;
  userIdentity: AccountData = {};
  rolesStringified = '';
  filterValue: string = '';
  dialogRef: MatDialogRef<ConfirmDialogComponent>;
  changedAccounts: Set<AccountData> = new Set<AccountData>();
  changedRows: Set<number> = new Set<number>();

  accountsWithChangedAccessLevels: Set<AccountData> = new Set<AccountData>();
  accountsWithChangedConfirm: Set<AccountData> = new Set<AccountData>();
  accountsWithChangedActive: Set<AccountData> = new Set<AccountData>();

  private responseCounter: number = 0;
  private successfullySentAccounts: Set<string> = new Set<string>();
  private faultlySentAccounts: Set<string> = new Set<string>();
  private submitStatusMessage: string = '';
  readonly avaliableRoles: SelectValue[] = SelectValues.roleSelectValues;

  constructor(private accountService: AccountService,
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
    this.loadData();
    this.updateRoles();
  }

  ngAfterViewChecked() {
    this.changeDetectorRef.detectChanges();
  }

  loadData() {
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
  }

  onAccessLevelChange(account: AccountData, rowId: number) {
    this.onAccountChange(account, rowId);
    this.accountsWithChangedAccessLevels.add(account);
  }

  onConfirmChange(account: AccountData, rowId: number) {

    this.dialogRef = this.dialog.open(ConfirmDialogComponent, {disableClose: true});
    this.dialogRef.componentInstance.text = this.translateService.instant("ACCOUNT.CONFIRM_CONFIRM");

    this.dialogRef.afterClosed().subscribe((userAccepted: boolean) => {
      if (userAccepted) {
        this.onAccountChange(account, rowId);
        this.accountsWithChangedConfirm.add(account);
      } else {
        let index: number = this.dataSource.data.findIndex((value: AccountData) => {
          return value.id === account.id;
        })
        this.dataSource.data[index].confirm = false;
      }
    })

  }

  onActiveChange(account: AccountData, rowId: number) {
    this.onAccountChange(account, rowId);
    this.accountsWithChangedActive.add(account);
  }

  
  submitAccounts() {
    this.submitAccessLevels();
    this.submitActive();
    this.submitConfirm();
  }

  private onAccountChange(account: AccountData, rowId: number) {
    this.changedAccounts.add(account);
    this.changedRows.add(rowId);
  }

  private submitAccessLevels() {
    this.accountsWithChangedAccessLevels.forEach((account: AccountData) => {
      this.accountService.alterAccountAccessLevel(account).subscribe(
      () => {
        let succMessage = this.translateService.instant("SUCCESS.ALTERED_AL_FOR_ACCOUNT");
        this.handleSubmit(succMessage + " " + account.login);
      }, 
      () => {
        let failMessage = this.translateService.instant("SUCCESS.FAILED_ALT_AL_FOR_ACCOUNT");
        this.handleSubmit(failMessage + " " + account.login);
      });
    })
  }

  private submitActive() {
    this.accountsWithChangedActive.forEach((account: AccountData) => {
      let isLocked: boolean = !account.active;

      if(isLocked) {
        this.accountService.lockAccount(account.id).subscribe(
          () => {
            let succMessage = this.translateService.instant("SUCCESS.LOCKED");
            this.handleSubmit(succMessage + " " + account.login);
          }, 
          () => {
            let failMessage = this.translateService.instant("SUCCESS.FAILED_TO_LOCK");
            this.handleSubmit(failMessage + " " + account.login);
          });
      } else {
        this.accountService.unlockAccount(account.id).subscribe(
          () => {
            let succMessage = this.translateService.instant("SUCCESS.UNLOCKED");
            this.handleSubmit(succMessage + " " + account.login);
          }, 
          () => {
            let failMessage = this.translateService.instant("SUCCESS.FAILED_TO_UNLOCK");
            this.handleSubmit(failMessage + " " + account.login);
          });
      }

    })
  }

  private submitConfirm() {
    this.accountsWithChangedConfirm.forEach((account: AccountData) => {
      this.accountService.confirmAccount(account.id).subscribe(
        () => {
          let succMessage = this.translateService.instant("SUCCESS.CONFIRMED");
          this.handleSubmit(succMessage + " " + account.login);
        }, 
        () => {
          let failMessage = this.translateService.instant("SUCCESS.FAILED_TO_CONFIRM");
          this.handleSubmit(failMessage + " " + account.login);
        }
      );
    })
  }

  private handleSubmit(message: string) {
    this.registerResponse(message);
  }

  private registerResponse(message: string) {
    this.responseCounter++;

    this.submitStatusMessage += message + " | ";

    let numberOfChangedAccounts = 
      this.accountsWithChangedConfirm.size + 
      this.accountsWithChangedAccessLevels.size + 
      this.accountsWithChangedActive.size;

    if(this.responseCounter === numberOfChangedAccounts) {

      this.notificationService.displayTranslatedNotification(this.submitStatusMessage);

      //end
      this.reinitializeStuff();
    }
  }

  private reinitializeStuff() {
    this.responseCounter = 0;
    this.accountsWithChangedAccessLevels.clear();
    this.accountsWithChangedActive.clear();
    this.accountsWithChangedConfirm.clear();
    this.changedRows.clear();
    this.changedAccounts.clear();
    this.successfullySentAccounts.clear();
    this.faultlySentAccounts.clear();
    this.submitStatusMessage = '';
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

    if (this.changedAccounts.size !== 0 || this.changedRows.size != 0) {

      this.dialogRef = this.dialog.open(ConfirmDialogComponent, {
        disableClose: false
      });

      this.dialogRef.componentInstance.text = "Zmiany nie zostaną zapisane. Kontynuować?";

      this.dialogRef.afterClosed().subscribe((userConfirmedOperation: boolean) => {
        if (userConfirmedOperation) {
          this.applyFilter(filterValue);
          this.reinitializeStuff();
          this.notificationService.displayTranslatedNotification("ACCOUNT.RESET_DATA_INFO");
        } else {
          this.filterValue = '';
        }
      })

    } else {
      this.applyFilter(filterValue);
    }
  }

  onPaginatorPageChange() {
    if (this.changedAccounts.size !== 0 || this.changedRows.size != 0) {
      this.notificationService.displayTranslatedNotification("ACCOUNT.RESET_DATA_INFO");
      this.reinitializeStuff();
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

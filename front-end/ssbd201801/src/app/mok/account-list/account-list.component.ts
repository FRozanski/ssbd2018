import {Component, OnInit, ViewChild, ChangeDetectorRef} from '@angular/core';
import {AccountService} from '../common/account.service';
import {HttpErrorResponse} from '@angular/common/http';
import {AccountData} from '../model/account-data';
import {environment} from '../../../environments/environment';
import {Router} from '@angular/router';
import {MatTableDataSource, MatSort, MatPaginator} from '@angular/material';
import {LocationService} from '../common/location.service';
import {TranslateService} from '@ngx-translate/core';
import {AuthUtilService} from '../common/auth-util.service';
import {SessionService} from '../common/session.service';

@Component({
  selector: 'app-account-list',
  templateUrl: './account-list.component.html',
  styleUrls: ['./account-list.component.css']
})
export class AccountListComponent implements OnInit {

  displayedColumns = [
    {def: 'login', showManager: true},
    {def: 'firstName', showManager: true},
    {def: 'surname', showManager: true},
    {def: 'email', showManager: true},
    {def: 'phone', showManager: true},
    {def: 'country', showManager: true},
    {def: 'city', showManager: true},
    {def: 'street', showManager: true},
    {def: 'streetNumber', showManager: true},
    {def: 'flatNumber', showManager: true},
    {def: 'postalCode', showManager: true},
    {def: 'edit', showManager: false},
    {def: 'changePassword', showManager: false},
  ];
  dataSource;

  userIdentity: AccountData = {};

  constructor(private accountService: AccountService,
              private router: Router,
              private locationService: LocationService,
              private translateService: TranslateService,
              private authUtil: AuthUtilService,
              private sessionService: SessionService) {
  }

  @ViewChild(MatSort) sort: MatSort;
  @ViewChild(MatPaginator) paginator: MatPaginator;

  ngOnInit() {
    this.locationService.passRouter('LOCATION.ACCOUNT_LIST_PAGE');
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

  getDisplayedColumns(): string[] {
    const isManager = this.hasRole('MANAGER') && !this.hasRole('ADMIN');
    return this.displayedColumns
      .filter(cd => !isManager || cd.showManager)
      .map(cd => cd.def);
  }

  applyFilter(filterValue: string) {
    filterValue = filterValue.trim();
    filterValue = filterValue.toLowerCase();
    this.dataSource.filter = filterValue;
  }

  onEditClick(account: AccountData) {
    this.router.navigate(['/accountEdit/' + account.id]);
  }


  onChangePasswordClick(account: AccountData) {
    this.accountService.passId(+account.id);
    this.router.navigate(['/changeOthersPassword']);
  }

  updateRoles() {
    this.sessionService.getMyIdentity().toPromise().then((data) => {
      this.userIdentity.roles = data.roles;
    }).catch((data) => {
      this.userIdentity.roles = this.translateService.instant('GUEST');
    });
  }

  hasRole(role: string): boolean {
    return this.authUtil.hasRole(role, this.userIdentity);
  }
}

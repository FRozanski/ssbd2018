import { Component, ViewChild, OnInit, Output, EventEmitter } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { AccountService } from '../common/account.service';
import { AccountData } from '../model/account-data';
import { Observable } from 'rxjs/Observable';
import { environment } from '../../environments/environment';
import { TranslateService } from '@ngx-translate/core';
import {SessionService} from '../common/session.service';
import { AuthUtilService } from '../common/auth-util.service';
import { AuthService } from '../common/auth.service';

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.css']
})
export class SidenavComponent implements OnInit {

  userIdentity: AccountData = {};
  roles: string = "";

  constructor(private sessionService: SessionService, private translateService: TranslateService, 
    private authUtil: AuthUtilService, private authService: AuthService) { 
    }

  ngOnInit() {
    this.sessionService.getMyIdentity().subscribe((data: AccountData) => {
      this.userIdentity = data;
    }, (errorResponse) => {
      console.log(this.translateService.instant(errorResponse.error.message));
    });
  }

  hasRole(role: string): boolean {
    return this.authUtil.hasRole(role, this.userIdentity);
  }
  isGuest() {
    return this.authUtil.isGuest, this.userIdentity;
  }

  onSidenavOpenedChange() {
    this.updateLoginAndRoles();
  }

  onLogoutClick() {
    this.authService.logout().subscribe(() => {
      this.updateLoginAndRoles();
    });
  }

  updateLoginAndRoles() {
    this.sessionService.getMyIdentity().toPromise().then((data) => {
      this.userIdentity.login = data.login;
      this.userIdentity.roles = data.roles;
      this.roles = JSON.stringify(data.roles);
    }).catch((data) => {
      this.userIdentity.login = this.translateService.instant('SIDE_NAV.GUEST');
      this.roles = this.translateService.instant('SIDE_NAV.GUEST');
    });
  }  
}

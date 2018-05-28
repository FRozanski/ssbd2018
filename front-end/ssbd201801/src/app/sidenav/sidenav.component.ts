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
  rolesStringified: string = "";

  constructor(private sessionService: SessionService, private translateService: TranslateService,
    private authUtil: AuthUtilService, private authService: AuthService) {
    }

  ngOnInit() {
    this.updateLoginAndRoles();
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
      this.rolesStringified = JSON.stringify(data.roles);
    }).catch((data) => {
      this.userIdentity.login = this.translateService.instant('GUEST');
      this.userIdentity.roles = this.translateService.instant('GUEST');
      this.rolesStringified = JSON.stringify(this.userIdentity.roles);
    });
  }

  hasRole(role: string): boolean {
    return this.authUtil.hasRole(role, this.userIdentity);
  }

}

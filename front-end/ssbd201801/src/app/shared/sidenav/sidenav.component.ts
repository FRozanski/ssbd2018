import { Component, ViewChild, OnInit, Output, EventEmitter } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { Observable } from 'rxjs/Observable';
import { TranslateService } from '@ngx-translate/core';
import { SessionService } from '../../mok/common/session.service';
import { AuthUtilService } from '../../mok/common/auth-util.service';
import { AuthService } from '../../mok/common/auth.service';
import { AccountData } from '../../mok/model/account-data';
import { NotificationService } from '../../mok/common/notification.service';
import { Route, Router } from '@angular/router';
import { LocationService } from '../../mok/common/location.service';


@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.css']
})
export class SidenavComponent implements OnInit {

  userIdentity: AccountData = {};
  rolesStringified: string = "";

  displayedRouter: string;

  constructor(
    private sessionService: SessionService,
    private translateService: TranslateService,
    private authUtil: AuthUtilService,
    private authService: AuthService,
    private notificationService: NotificationService,
    private router: Router,
    private locationService: LocationService) {
    }

  ngOnInit() {
    this.sessionService.getMyIdentity().subscribe((data: AccountData) => {
      this.userIdentity = data;
    }, () => {
      this.userIdentity = {
        login: "Guest",
        roles: ['GUEST']
      }
    });
    this.locationService.currentRouter.subscribe(router => {
      console.log("router = " + router);
      this.displayedRouter = router;
    });
    this.updateLoginAndRoles();
  }

  onSidenavOpenedChange() {
    this.updateLoginAndRoles();
  }

  onLogoutClick() {
    this.authService.logout().subscribe(() => {
      this.updateLoginAndRoles();
      this.notificationService.displayTranslatedNotification("SUCCESS.LOGOUT");
      this.router.navigate(['/main']);
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

  isMainPage(router: string): boolean {
    if (router.length <= 0 || router === 'LOCATION.MAIN_PAGE') return true;
    else return false;
  }
}

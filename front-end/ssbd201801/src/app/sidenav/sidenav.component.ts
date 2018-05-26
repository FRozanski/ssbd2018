import { Component, ViewChild, OnInit } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { AccountService } from '../common/account.service';
import { AccountData } from '../model/account-data';
import { Observable } from 'rxjs/Observable';
import { environment } from '../../environments/environment';
import { TranslateService } from '@ngx-translate/core';
import {SessionService} from '../common/session.service';
import { AuthUtilService } from '../common/auth-util.service';

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.css']
})
export class SidenavComponent implements OnInit {

  userIdentity: AccountData = {};
  roles: string = "";

  constructor(private sessionService: SessionService, private translateService: TranslateService, private authUtil: AuthUtilService) { }

  ngOnInit() {
    this.sessionService.getMyIdentity().subscribe((data: AccountData) => {
      this.userIdentity = data;
    });
  }

  hasRole(role: string): boolean {
    return this.authUtil.hasRole(role, this.userIdentity);
  }
  isGuest() {
    return this.authUtil.isGuest, this.userIdentity;
  }

  onSidenavOpenedChange() {
    this.sessionService.getMyIdentity().toPromise().then((data) => {
      this.userIdentity.login = data.login;
      this.roles = JSON.stringify(data.roles);
    }).catch((data) => {
      this.userIdentity.login = "Gość";// translate
      this.roles = "[GUEST]"
    });
  }

}

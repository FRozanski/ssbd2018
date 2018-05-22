import { Component, ViewChild, OnInit } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { AccountService } from '../common/account.service';
import { AccountData } from '../model/account-data';
import { Observable } from 'rxjs/Observable';
import { environment } from '../../environments/environment';
import { TranslateService } from '@ngx-translate/core';

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.css']
})
export class SidenavComponent implements OnInit {

  userIdentity: AccountData = {};

  constructor(private accountService: AccountService, private translateService: TranslateService) { }

  ngOnInit() {

    this.accountService.getCurrentUserIdentity().subscribe((data: AccountData) => {
      this.userIdentity = data;
    });

  }

  hasRole(role: string): boolean {  

    let hasRole: boolean = false;

    if(this.userIdentity && this.userIdentity.roles) {
      hasRole = this.userIdentity.roles.indexOf(role) !== -1;
    }

    return hasRole;
  }

  isGuest() {
    if (!this.userIdentity) return true;
    if (!this.userIdentity.roles) return true;
    if (this.userIdentity.roles.length === 0) return true;
    else return false; 
  }

  getUserLogin() {
    if(this.userIdentity && this.userIdentity.login) return this.userIdentity.login;
    else return "Guest";
  }

  getUserAccessLevels() {
    if(this.userIdentity && this.userIdentity.roles) 
    {
      let roles = this.userIdentity.roles;
      if(roles.indexOf("VIRTUAL") !== -1) {
        return this.translateService.instant("HOME.ACTIVATE_ACCOUNT");
      } else {
        return JSON.stringify(roles);
      }
    }
    else return '["GUEST"]';
  }

  onLoginClick() {
    window.location.href= environment.apiUrl + '/login/login.html';
  }



}

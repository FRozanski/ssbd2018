import { Component, ViewChild, OnInit } from '@angular/core';
import { MatSidenav } from '@angular/material/sidenav';
import { AccountService } from '../common/account.service';
import { AccountData } from '../model/account-data';
import { Observable } from 'rxjs/Observable';

@Component({
  selector: 'app-sidenav',
  templateUrl: './sidenav.component.html',
  styleUrls: ['./sidenav.component.css']
})
export class SidenavComponent implements OnInit {

  userIdentity: AccountData = {};

  constructor(private accountService: AccountService) { }

  ngOnInit() {

    this.accountService.getCurrentUserIdentity().subscribe((data: AccountData) => {
      this.userIdentity = data;
    });

  }

  hasRole(role: string): boolean {  

    let hasRole: boolean = false;

    if(this.userIdentity.roles) {
      hasRole = this.userIdentity.roles.indexOf(role) !== -1;
    }

    return hasRole;
  }

  isGuest() {
    if (!this.userIdentity.roles) return false;
    if (this.userIdentity.roles.length === 0) return true;
    else return false; 
  }

}

import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { AccountService } from '../common/account.service';
import { HttpErrorResponse } from '@angular/common/http';
import { DeployPrefix } from '../common/constants';
import { AccountData } from '../model/account';

@Component({
  selector: 'app-account-list',
  templateUrl: './account-list.component.html',
  styleUrls: ['./account-list.component.css']
})
export class AccountListComponent implements OnInit {

  displayedColumns = [
    'city', 'confirm', 'country', 'email', 'firstName', 
    'lastName', 'login', 'numberOfLogins', 'numberOfOrders', 
    'numberOfProducts', 'phone', 'postalCode', 'street', 'streetNumber'
  ];
  dataSource;

  constructor (private accountService: AccountService) { }

  ngOnInit() {
    this.accountService.getAllAccounts().subscribe((data)=> {
      this.dataSource = data;
    }, ()=> {
      window.location.href = "/" + DeployPrefix + "/login/login.html";
    });
  }

 

}
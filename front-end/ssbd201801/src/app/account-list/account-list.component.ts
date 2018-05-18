import { Component, OnInit } from '@angular/core';
import { AccountService } from '../common/account.service';

@Component({
  selector: 'app-account-list',
  templateUrl: './account-list.component.html',
  styleUrls: ['./account-list.component.css']
})
export class AccountListComponent implements OnInit {

  accounts: Account[] = [];

  constructor(private accountService: AccountService) { }

  ngOnInit() {

    // todo: use async pipe.
    this.accountService.getAllAcounts().subscribe((data: Account[]) => {
      this.accounts = data;
    })
  }

}

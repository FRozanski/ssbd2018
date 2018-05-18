import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { HttpClient } from '@angular/common/http';
import { DeployPrefix } from '../common/constants';
import 'rxjs/add/observable/of';
import { AccountData } from '../model/account';

@Injectable()
export class AccountService {

  readonly uri: string = "/" + DeployPrefix + "/webresources";

  constructor(private httpClient: HttpClient) { }

  // getAllAccounts() : Observable<Account[]> {
  //   return this.httpClient.get<Account[]>(this.uri + '/account');
  // }

  getAllAccounts() : Observable<AccountData[]> {

    let a: AccountData =  {
      city: "abc",
      confirm: true,
      country: "abc",
      email: "abc",
      firstName: "abc",
      lastName: "abc",
      login: "abc",
      numberOfLogins: 1,
      numberOfOrders: 1,
      numberOfProducts: 1,
      phone: "abc",
      postalCode: "abc",
      street: "abc",
      streetNumber: "abc"
    };

    console.log(JSON.stringify(a));

    return Observable.of([
      a
    ])
  }
}

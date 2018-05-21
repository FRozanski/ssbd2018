import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { DeployPrefix } from '../common/constants';
import 'rxjs/add/observable/of';
import 'rxjs/add/observable/throw';
import { AccountData } from '../model/account-data';

@Injectable()
export class AccountService {

  readonly uri: string = "/" + DeployPrefix + "/webresources";

  constructor(private httpClient: HttpClient) { }

  getAllAccounts(): Observable<AccountData[]> {
    return this.httpClient.get<AccountData[]>(this.uri + '/account');
  }

  // mock

  // registerAccount(account: AccountData): Observable<AccountData> {

  //   let arr = [ 
  //     'error.login_unique',
  //     'error.different_passwords',
  //     'error_batyskaf'
  //   ];

  //   let error: Response = new Response(arr.toString());
  //   return Observable.throw(error);
  // }

  // real

  registerAccount(account: AccountData): Observable<AccountData>{
    return this.httpClient.post<AccountData>(this.uri + '/account/registerAccount', account)
  }
}

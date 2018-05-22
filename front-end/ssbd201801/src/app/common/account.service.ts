import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { DeployPrefix } from '../common/constants';
import 'rxjs/add/observable/of';
import { AccountData } from '../model/account-data';

@Injectable()
export class AccountService {

  readonly uri: string = "/" + DeployPrefix + "/webresources";

  constructor(private httpClient: HttpClient) { }

  getAllAccounts() : Observable<AccountData[]> {
    return this.httpClient.get<AccountData[]>(this.uri + '/account');
  }

  // mock
  registerAccount(account: AccountData) : Observable<HttpResponse<any>>{

    let response = new HttpResponse<any>({
      status: 500,
      body: {
        errors: [
          'error.login_unique',
          'error.different_passwords',
          'error_batyskaf'
        ]
      }
    });    

    return Observable.of(response);
  }
}

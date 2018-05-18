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

  getAllAccounts() : Observable<Account[]> {
    return this.httpClient.get<Account[]>(this.uri + '/account');
  }
}

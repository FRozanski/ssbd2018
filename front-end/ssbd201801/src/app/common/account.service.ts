import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { environment } from '../../environments/environment';
import 'rxjs/add/observable/of';
import 'rxjs/add/observable/throw';
import { AccountData } from '../model/account-data';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';

@Injectable()
export class AccountService {

  readonly uri: string = environment.apiUrl + '/webresources/account';

  private idSource = new BehaviorSubject<string>('default id');

  currentId = this.idSource.asObservable();

  constructor(private httpClient: HttpClient) { }

  getAllAccounts(): Observable<AccountData[]> {
    return this.httpClient.get<AccountData[]>(this.uri);
  }

  registerAccount(account: AccountData): Observable<AccountData> {
    return this.httpClient.post<AccountData>(this.uri + '/registerAccount', account);
  }

  getCurrentUserIdentity(): Observable<AccountData> {
    return this.httpClient.get<AccountData>(this.uri + '/myIdentity');
  }

  changePassword(account: AccountData): Observable<AccountData> {
    return this.httpClient.put<AccountData>(this.uri + '/changePassword', account);
  }

  getAccountToEdit(id: string): Observable<AccountData> {
    return this.httpClient.get<AccountData>(this.uri + '/' + id);
  }

  changeOthersPassword(account: AccountData): Observable<AccountData>{
    return this.httpClient.put<AccountData>(this.uri + '/changeOthersPassword', account);
  }

  passId(id: string) {
    this.idSource.next(id);
  }
}

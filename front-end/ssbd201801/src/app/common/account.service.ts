import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import {HttpClient, HttpResponse, HttpRequest, HttpParams} from '@angular/common/http';
import { environment } from '../../environments/environment'
import { AccountData } from '../model/account-data';
import { post } from 'selenium-webdriver/http';
import { AuthUtilService } from './auth-util.service';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import 'rxjs/add/observable/of';
import 'rxjs/add/observable/throw';

@Injectable()
export class AccountService {

  readonly uri: string = environment.apiUrl + '/webresources/account';

  private idSource = new BehaviorSubject<number>(0);

  currentId = this.idSource.asObservable();

  constructor(private httpClient: HttpClient, private authUtilService: AuthUtilService) { }

  getAllAccounts(): Observable<AccountData[]> {
    return this.httpClient.get<AccountData[]>(this.uri);
  }

  getAccountToEdit(id: number): Observable<AccountData> {
    return this.httpClient.get<AccountData>(this.uri + "/" + id);
  }

  getMyAccountToEdit(): Observable<AccountData> {
    return this.httpClient.get<AccountData>(this.uri + "/myAccountToEdit");
  }

  updateMyAccount(account: AccountData) {
    console.log("Sending account to api: ", account);
    return this.httpClient.put<AccountData>(this.uri + "/updateMyAccount", account);
  }

  updateAccount(account: AccountData): Observable<any> {
    return this.httpClient.put<AccountData>(this.uri + "/updateAccount", account);
  }

  getAccessLevel(): Observable<AccountData> {
    return this.httpClient.get<AccountData>(this.uri + '/allAccessLevel');
  }

  registerAccount(account: AccountData): Observable<AccountData> {
    return this.httpClient.post<AccountData>(this.uri + '/registerAccount', account);
  }

  changeMyPassword(account: AccountData): Observable<AccountData> {
    return this.httpClient.put<AccountData>(this.uri + '/changeMyPassword', account);
  }

  changeOthersPassword(account: AccountData): Observable<AccountData> {
    return this.httpClient.put<AccountData>(this.uri + '/changeOthersPassword', account);
  }

  lockAccount(accountId: number) {
    const params = new HttpParams()
      .set('accountId', accountId.toString());
    return this.httpClient.put(this.uri + '/lockAccount', {params});
  }

  unlockAccount(accountId: number) {
    const params = new HttpParams()
      .set('accountId', accountId.toString());
    return this.httpClient.put(this.uri + '/unlockAccount', {params});
  }

  addAccessLevelToAccount(accountId: number, alevelId: number) {
    const params = new HttpParams()
      .set('accountId', accountId.toString())
      .set('alevelId', alevelId.toString());
    return this.httpClient.put(this.uri + '/addAccessLevel', {params});
  }

  deleteAccountAlevel(accountId: number, alevelId: number) {
    const params = new HttpParams()
      .set('accountId', accountId.toString())
      .set('alevelId', alevelId.toString());
    return this.httpClient.delete(this.uri + '/deleteAccessLevel', {params});
  }

  confirmAccount(accountId: number) {
    const params = new HttpParams()
      .set('accountId', accountId.toString());
    return this.httpClient.put(this.uri + '/confirmAccount', {params});
  }

  confirmAccountByToken(token: string) {
    const params = new HttpParams()
      .set('token', token);
    return this.httpClient.put(this.uri + '/confirmAccount', {params});
  }

  passId(id: number) {
    this.idSource.next(id);
  }
}

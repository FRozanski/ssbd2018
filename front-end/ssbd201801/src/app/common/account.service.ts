import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { HttpClient, HttpResponse, HttpRequest } from '@angular/common/http';
import { environment } from '../../environments/environment'
import 'rxjs/add/observable/of';
import 'rxjs/add/observable/throw';
import { AccountData } from '../model/account-data';
import { post } from 'selenium-webdriver/http';
import { AuthUtilService } from './auth-util.service';

@Injectable()
export class AccountService {

  readonly uri: string = environment.apiUrl + "/webresources/account";

  constructor(private httpClient: HttpClient, private authUtilService: AuthUtilService) { }

  getAllAccounts(): Observable<AccountData[]> {
    return this.httpClient.get<AccountData[]>(this.uri);
  }

  registerAccount(account: AccountData): Observable<AccountData>{
    return this.httpClient.post<AccountData>(this.uri + '/registerAccount', account)
  }

  getCurrentUserIdentity(): Observable<AccountData> {
    return this.httpClient.get<AccountData>(this.uri + "/myIdentity");
  }

  editAccount(id: number, account: AccountData): Observable<any> {
    return this.httpClient.put<AccountData>(this.uri + "/" + id, account);
  }

  editLoggedUserAccount(account: AccountData) {
    return this.httpClient.put<AccountData>(this.uri + "/updateMyAccount", account);
  }

  getAccountToEdit(id: number): Observable<AccountData> {
    return this.httpClient.get<AccountData>(this.uri + "/" + id);
  }

  getLoggedUserDataToEdit(): Observable<AccountData> {
    return this.httpClient.get<AccountData>(this.uri + "/myAccountToEdit");
  }

}

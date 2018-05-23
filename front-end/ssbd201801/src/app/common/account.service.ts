import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { HttpClient, HttpResponse, HttpRequest } from '@angular/common/http';
import { environment } from '../../environments/environment'
import { AccountData } from '../model/account-data';
import { post } from 'selenium-webdriver/http';
import { AuthUtilService } from './auth-util.service';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';
import 'rxjs/add/observable/of';
import 'rxjs/add/observable/throw';

@Injectable()
export class AccountService {

  readonly uri: string = environment.apiUrl + "/webresources/account";

  constructor(private httpClient: HttpClient, private authUtilService: AuthUtilService) { }

  private loginSource = new BehaviorSubject<string>('default login');

  currentLogin = this.loginSource.asObservable();

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

  editAccount(encodedId: string, account: AccountData): Observable<any> {
    return this.httpClient.put<AccountData>(this.uri + "/" + encodedId, {
      "city": account.city,
      "country": account.country,
      "email": account.email,
      "name": account.name,
      "phone": account.phone,
      "postalCode": account.postalCode,
      "street": account.street,
      "streetNumber": account.streetNumber,
      "surname": account.surname,
      "version": 0
    });
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

  changeOthersPassword(account: AccountData): Observable<AccountData> {
    return this.httpClient.put<AccountData>(this.uri + '/changeOthersPassword', account);
  }

  passLogin(login: string) {
    this.loginSource.next(login);
  }
}

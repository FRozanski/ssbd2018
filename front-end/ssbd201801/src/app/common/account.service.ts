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

  readonly uri: string = environment.apiUrl + '/webresources/account';

  private idSource = new BehaviorSubject<number>(0);

  currentId = this.idSource.asObservable();

  constructor(private httpClient: HttpClient, private authUtilService: AuthUtilService) { }

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

  changeOthersPassword(account: AccountData): Observable<AccountData> {
    return this.httpClient.put<AccountData>(this.uri + '/changeOthersPassword', account);
  }

  editAccount(account: AccountData): Observable<any> {
    return this.httpClient.put<AccountData>(this.uri + "/updateAccount", {
      "id": account.id,
      "city": account.city,
      "country": account.country,
      "email": account.email,
      "name": account.name,
      "phone": account.phone,
      "postalCode": account.postalCode,
      "street": account.street,
      "streetNumber": account.streetNumber,
      "surname": account.surname
    });
  }

  editLoggedUserAccount(account: AccountData) {
    console.log("Sending account to api: ", account);
    return this.httpClient.put<AccountData>(this.uri + "/updateMyAccount", account);
  }

  getAccountToEdit(id: number): Observable<AccountData> {
    return this.httpClient.get<AccountData>(this.uri + "/" + id);
  }

  getLoggedUserDataToEdit(): Observable<AccountData> {
    return this.httpClient.get<AccountData>(this.uri + "/myAccountToEdit");
  }

  passId(id: number) {
    this.idSource.next(id);
  }
}

import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {Observable} from 'rxjs/Observable';
import {AccountData} from '../model/account-data';

@Injectable()
export class RegistrationConfirmService {

  readonly uri: string = environment.apiUrl + '/webresources/registrationConfirm';

  constructor(private httpClient: HttpClient) { }

  confirmAccount(token: string): Observable<AccountData> {
    const params = new HttpParams()
      .set('token', token);
    return this.httpClient.get<AccountData>(this.uri, {params});
  }
}

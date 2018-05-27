import { Injectable } from '@angular/core';
import {environment} from '../../environments/environment';
import {Observable} from 'rxjs/Observable';
import {AccountData} from '../model/account-data';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class SessionService {

  readonly uri: string = environment.apiUrl + '/webresources/session';

  constructor(private httpClient: HttpClient) { }

  getMyLogin(): Observable<AccountData> {
    return this.httpClient.get<AccountData>(this.uri + '/myLogin');
  }

  getMyIdentity(): Observable<AccountData> {
    return this.httpClient.get<AccountData>(this.uri + '/myIdentity');
  }
}

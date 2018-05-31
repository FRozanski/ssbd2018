import { Injectable, EventEmitter } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { AccountData } from '../model/account-data';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';


@Injectable()
export class SessionService {

  readonly uri: string = environment.apiUrl + '/webresources/session';

  constructor(private httpClient: HttpClient) {
  }

  getMyLogin(): Observable<AccountData> {
    return this.httpClient.get<AccountData>(this.uri + '/myLogin');
  }

  getMyIdentity(): Observable<AccountData> {
    return this.httpClient.get<AccountData>(this.uri + '/myIdentity');
  }


}

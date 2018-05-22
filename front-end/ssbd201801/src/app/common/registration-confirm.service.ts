import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {environment} from '../../environments/environment';
import {TokenData} from '../model/token-data';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class RegistrationConfirmService {

  readonly uri: string = environment.apiUrl + '/webresources/registrationConfirm';

  constructor(private httpClient: HttpClient) { }

  confirmAccount(token: string): Observable<TokenData[]> {
    const params = new HttpParams()
      .set('token', token);
    return this.httpClient.get<TokenData[]>(this.uri, {params});
  }
}

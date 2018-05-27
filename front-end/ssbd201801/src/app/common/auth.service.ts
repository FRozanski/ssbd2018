import { Injectable } from '@angular/core';
import { Credentials } from '../model/credentials';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';

@Injectable()
export class AuthService {

  readonly uri: string = environment.apiUrl + '/webresources/auth';

  constructor(private httpClient: HttpClient) { }

  login(credentials: Credentials) {
    return this.httpClient.post(this.uri + '/login', credentials)
  }

  logout() {
    return this.httpClient.post(this.uri + '/logout', {});
  }
}

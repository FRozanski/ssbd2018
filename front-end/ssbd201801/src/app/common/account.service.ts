import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { HttpClient } from '@angular/common/http';


@Injectable()
export class AccountService {

  readonly uri: string = "/ssbd01-06146406291034321158.2.0/webresources";

  constructor(private httpClient: HttpClient) { }

  getAllAcounts() : Observable<Account[]> {
    return this.httpClient.get<Account[]>(this.uri + '/account');
  }

}

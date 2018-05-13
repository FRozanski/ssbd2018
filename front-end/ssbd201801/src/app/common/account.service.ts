import { Injectable } from '@angular/core';
import { Observable } from 'rxjs/Observable';
import { HttpClient } from '@angular/common/http';


@Injectable()
export class AccountService {

  readonly uri: string = "https://studapp.it.p.lodz.pl:8401/ssbd01-02326615179242781552.2.0";

  constructor(private httpClient: HttpClient) { }

  getAllAcounts() : Observable<Account[]> {
    return this.httpClient.get<Account[]>(this.uri + '/webresources/account');
  }

}

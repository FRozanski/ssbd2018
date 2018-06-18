import { Injectable } from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import {ShippingMethod} from '../model/shipping-method';

@Injectable()
export class ShippingMethodService {

  readonly uri: string = environment.apiUrl + '/webresources/shipping';

  constructor(private httpClient: HttpClient) { }

  getAllShippingMethods(): Observable<ShippingMethod[]> {
    return this.httpClient.get<ShippingMethod[]>(this.uri);
  }

}

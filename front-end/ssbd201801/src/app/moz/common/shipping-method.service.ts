import { Injectable } from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {HttpClient, HttpParams} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import { ShippingMethodData } from '../model/shippingMethod-data';
import {ShippingMethod} from '../model/shipping-method';

@Injectable()
export class ShippingMethodService {

  readonly uri: string = environment.apiUrl + '/webresources/shipping';

  constructor(private httpClient: HttpClient) { }

  getAllShippingMethods(): Observable<ShippingMethod[]> {
    return this.httpClient.get<ShippingMethod[]>(this.uri);
  }

  activateShippingMethod(shippingMethodId: number) {
    const params = new HttpParams()
      .set('methodId', shippingMethodId.toString());
    return this.httpClient.put(this.uri + '/activate', null, {params});
  }

  deactivateShippingMethod(shippingMethodId: number) {
    const params = new HttpParams()
      .set('methodId', shippingMethodId.toString());
    return this.httpClient.put(this.uri + '/deactivate', null, {params});
  }

  addShippingMethod(shipingMethod: ShippingMethodData) {
    return this.httpClient.post<ShippingMethodData>(this.uri + '/addShippingMethod', shipingMethod);
  }
}

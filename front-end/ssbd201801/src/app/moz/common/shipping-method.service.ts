import { Injectable } from '@angular/core';
import {Observable} from 'rxjs/Observable';
import {Order} from '../model/order';
import {HttpClient} from '@angular/common/http';
import {environment} from '../../../environments/environment';
import { ShippingMethodData } from '../model/shippingMethod-data';

@Injectable()
export class ShippingMethodService {

  readonly uri: string = environment.apiUrl + '/webresources/shipping';

  constructor(private httpClient: HttpClient) { }

  getAllShippingMethods(): Observable<Order[]> {
    return this.httpClient.get<Order[]>(this.uri);
  }

  addShippingMethod(shipingMethod: ShippingMethodData) {
    return this.httpClient.post<ShippingMethodData>(this.uri + '/addShippingMethod', shipingMethod);
  }
}

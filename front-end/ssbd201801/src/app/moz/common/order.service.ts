import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Order } from '../model/order';
import { Observable } from 'rxjs/Observable';
import {OrderData} from '../../mop/model/order-data';

@Injectable()
export class OrderService {

  readonly uri: string = environment.apiUrl + '/webresources/order';

  constructor(private httpClient: HttpClient) { }

  getAllOrders(): Observable<Order[]> {
    return this.httpClient.get<Order[]>(this.uri);
  }

  getOrdersBoughtByUser(): Observable<Order[]> {
    return this.httpClient.get<Order[]>(this.uri + '/myBought');
  }

  getOrdersSoldByUser(): Observable<Order[]> {
    return this.httpClient.get<Order[]>(this.uri + '/mySold');
  }

  makeOrder(order: OrderData) {
    return this.httpClient.post<OrderData>(this.uri + '/makeOrder', order);
  }
}


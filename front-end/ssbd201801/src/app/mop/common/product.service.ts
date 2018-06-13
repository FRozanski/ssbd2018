import { Injectable } from '@angular/core';
import {environment} from '../../../environments/environment';
import {AccountData} from '../../mok/model/account-data';
import {Observable} from 'rxjs/Observable';
import {ProductData} from '../model/product-data';
import {HttpClient} from '@angular/common/http';

@Injectable()
export class ProductService {

  readonly uri: string = environment.apiUrl + '/webresources/product';

  constructor(private httpClient: HttpClient) { }

  getAllProducts(): Observable<ProductData[]> {
    return this.httpClient.get<ProductData[]>(this.uri);
  }

  getMyProducts(): Observable<ProductData[]> {
    return this.httpClient.get<ProductData[]>(this.uri + 'myProducts');
  }
}

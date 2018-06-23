import { Injectable } from '@angular/core';
import {environment} from '../../../environments/environment';
import {Observable} from 'rxjs/Observable';
import {ProductData} from '../model/product-data';
import {HttpClient, HttpParams} from '@angular/common/http';
import {NewProductData} from '../model/new-product-data';
import {EditProductData} from '../model/edit-product-data';

@Injectable()
export class ProductService {

  readonly uri: string = environment.apiUrl + '/webresources/product';

  constructor(private httpClient: HttpClient) { }

  getAllProducts(): Observable<ProductData[]> {
    return this.httpClient.get<ProductData[]>(this.uri);
  }

  getMyProducts(): Observable<ProductData[]> {
    return this.httpClient.get<ProductData[]>(this.uri + '/myProducts');
  }

  getAllActiveProductWithActiveCategory(): Observable<ProductData[]> {
    return this.httpClient.get<ProductData[]>(this.uri + '/activeProducts');
  }

  addProduct(product: NewProductData): Observable<NewProductData> {
    return this.httpClient.post<NewProductData>(this.uri + '/addProduct', product);
  }

  getProductToEdit(id: number): Observable<EditProductData> {
    return this.httpClient.get<EditProductData>(this.uri + '/productToEdit?productId=' + id);
  }

  updateProduct(product: EditProductData): Observable<EditProductData> {
    return this.httpClient.put<EditProductData>(this.uri + '/editProduct', product);
  }

  activateProduct(productId: number) {
    const params = new HttpParams()
      .set('productId', productId.toString());
    return this.httpClient.put(this.uri + '/activeProduct', null, {params});
  }

  deactivateProduct(productId: number) {
    const params = new HttpParams()
      .set('productId', productId.toString());
    return this.httpClient.put(this.uri + '/deactiveProduct', null, {params});
  }
}

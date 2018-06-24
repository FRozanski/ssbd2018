import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment.local';
import { HttpClient, HttpParams } from '@angular/common/http';
import { CategoryData } from '../model/category-data';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class CategoryService {

  private uri: string = environment.apiUrl + '/webresources/category';

  constructor(private httpClient: HttpClient) { }

  getAllCategories(): Observable<CategoryData[]> {
    return this.httpClient.get<CategoryData[]>(this.uri);
  }

  activateCategory(categoryId: number) {
    const params = new HttpParams()
      .set('categoryId', categoryId.toString());
    return this.httpClient.put(this.uri + '/activateCategory', null, {params});
  }

  deactivateCategory(categoryId: number) {
    const params = new HttpParams()
      .set('categoryId', categoryId.toString());
    return this.httpClient.put(this.uri + '/deactivateCategory', null, {params});
  }

  addCategory(newCategory: CategoryData) {
    return this.httpClient.put(this.uri + '/addCategory', newCategory);
  }

}

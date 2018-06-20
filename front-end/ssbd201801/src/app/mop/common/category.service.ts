import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment.local';
import { HttpClient } from '@angular/common/http';
import { CategoryData } from '../model/category-data';
import {Observable} from 'rxjs/Observable';

@Injectable()
export class CategoryService {

  private uri: string = environment.apiUrl + '/webresources/category';

  constructor(private httpClient: HttpClient) { }

  getAllCategories(): Observable<CategoryData[]> {
    return this.httpClient.get<CategoryData[]>(this.uri);
  }

}

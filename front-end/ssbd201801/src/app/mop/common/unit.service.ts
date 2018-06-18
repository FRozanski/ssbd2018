import { Injectable } from '@angular/core';
import {environment} from '../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {UnitData} from '../model/unit-data';

@Injectable()
export class UnitService {

  readonly uri: string = environment.apiUrl + '/webresources/unit';

  constructor(private httpClient: HttpClient) { }

  getAllUnits(): Observable<UnitData[]> {
    return this.httpClient.get<UnitData[]>(this.uri);
  }
}

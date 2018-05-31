import { Injectable } from '@angular/core';
import {BehaviorSubject} from 'rxjs/BehaviorSubject';

@Injectable()
export class LocationService {

  private routerSource = new BehaviorSubject<string>('');
  currentRouter = this.routerSource.asObservable();

  constructor() { }

  passRouter(router: string) {
    this.routerSource.next(router);
  }
}

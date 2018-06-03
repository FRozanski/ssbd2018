import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpResponse,
  HttpErrorResponse,
} from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import {ErrorHandlerService} from './error-handler.service';
import 'rxjs/add/operator/do';

@Injectable()
export class RequestInterceptorService implements HttpInterceptor {

  constructor(
    public errorHandlerService: ErrorHandlerService
  ) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(request).do((event: HttpEvent<any>) => {}, (err: any) => {
      if (err instanceof HttpErrorResponse) {
        this.errorHandlerService.handleError(err);
      }
    });
  }

}

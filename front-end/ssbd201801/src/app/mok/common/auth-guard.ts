import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot, Router} from '@angular/router';
import {Observable} from 'rxjs/Observable';
import {AuthUtilService} from './auth-util.service';
import {AccountData} from '../model/account-data';
import {Injectable} from '@angular/core';
import {SessionService} from './session.service';
import {TranslateService} from '@ngx-translate/core';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';
import {HttpErrorResponse} from '@angular/common/http';

@Injectable()
export class AuthGuard implements CanActivate {

  protected identity: AccountData;

  userIdentity: AccountData = {};

  constructor(protected authUtil: AuthUtilService,
              protected sessionService: SessionService,
              private translateService: TranslateService,
              private router: Router) {

    this.sessionService.getMyIdentity().subscribe((data: AccountData) => {
      this.identity = data;
    }, () => {
      this.identity = {
        login: 'Guest',
        roles: ['GUEST']
      };
    });
  }

  canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<boolean> {
    return this.sessionService.getMyIdentity().map(data => {
      this.userIdentity.roles = data.roles;
      const can = this.authUtil.isExpectedRoleInUserRoles(route.data.expectedRoles, this.userIdentity.roles);
      this.throwErrorIfCannot(can, route);
      return can;
    }).catch((data) => {
      this.userIdentity.roles = this.translateService.instant('GUEST');
      const can = this.authUtil.isExpectedRoleInUserRoles(route.data.expectedRoles, this.userIdentity.roles);
      this.throwErrorIfCannot(can, route);
      return Observable.of(can);
    });
  }

  private throwErrorIfCannot(can, route: ActivatedRouteSnapshot) {
    if (can === false) {
      const error = new HttpErrorResponse({
        status: 403,
        statusText: this.translateService.instant('ERROR.FORBIDDEN'),
        url: route.url.toString(),
      });
      this.router.navigate(['/error'], {queryParams: error});
    }
  }
}

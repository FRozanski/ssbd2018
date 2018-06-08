import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot} from '@angular/router';
import {Observable} from 'rxjs/Observable';
import {AuthUtilService} from './auth-util.service';
import {AccountData} from '../model/account-data';
import {Injectable} from '@angular/core';
import {SessionService} from './session.service';
import {TranslateService} from '@ngx-translate/core';
import 'rxjs/add/operator/map';
import 'rxjs/add/operator/catch';

@Injectable()
export class AuthGuard implements CanActivate {

  protected identity: AccountData;

  userIdentity: AccountData = {};

  constructor(protected authUtil: AuthUtilService,
              protected sessionService: SessionService,
              private translateService: TranslateService) {

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

      // return this.authUtil.hasRole(route.data.expectedRole, this.userIdentity);
      console.log('route.data.expectedRole = ' + route.data.expectedRole);
      console.log('this.userIdentity.roles = ' + this.userIdentity.roles);
      console.log('1 = ' + this.authUtil.isExpectedRoleInUserRoles(route.data.expectedRole, this.userIdentity.roles));
      return this.authUtil.isExpectedRoleInUserRoles(route.data.expectedRole, this.userIdentity.roles);
    }).catch((data) => {
      this.userIdentity.roles = this.translateService.instant('GUEST');
      console.log('2 = ' + this.authUtil.isExpectedRoleInUserRoles(route.data.expectedRole, this.userIdentity.roles));
      return Observable.of(this.authUtil.isExpectedRoleInUserRoles(route.data.expectedRole, this.userIdentity.roles));
    });
  }

}

import {AccountData} from '../model/account-data';
import {Injectable} from '@angular/core';

@Injectable()
export class AuthUtilService {

  public hasRole(role: string, identity: AccountData): boolean {
    let hasRole = false;
    if (identity && identity.roles) {
      hasRole = identity.roles.indexOf(role) !== -1;
    }
    return hasRole;
  }

  public isExpectedRoleInUserRoles(expectedRole: any, roles: string[]): boolean {
    let is = false;
    if (expectedRole && roles) {
      is = roles.indexOf(expectedRole) !== -1;
    }
    return is;
  }
}

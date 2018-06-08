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

  public isExpectedRoleInUserRoles(expectedRoles: string[], roles: string[]): boolean {
    let is = false;
    if (expectedRoles && roles) {
      for (const expectedRole of expectedRoles) {
        const isCurrent = roles.indexOf(expectedRole) !== -1;
        if (isCurrent) {
          is = isCurrent;
          break;
        }
      }
    }
    return is;
  }
}

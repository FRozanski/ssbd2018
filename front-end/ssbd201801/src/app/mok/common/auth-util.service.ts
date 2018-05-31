import { AccountData } from "../model/account-data";
import { Injectable } from "@angular/core";

@Injectable()
export class AuthUtilService {


    public hasRole(role: string, identity: AccountData): boolean {
        let hasRole: boolean = false;
        if(identity && identity.roles) {
          hasRole = identity.roles.indexOf(role) !== -1;
        }
        return hasRole;
      }
}

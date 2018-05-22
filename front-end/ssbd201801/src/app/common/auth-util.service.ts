import { AccountData } from "../model/account-data";
import { Injectable } from "@angular/core";

@Injectable()
export class AuthUtilService {

    public isVirtual(identity: AccountData) : boolean {
        return this.hasRole("VIRTUAL", identity);
    }

    public isManager(identity: AccountData) : boolean {
        return this.hasRole("MANAGER", identity);
    }

    public isAdmin(identity: AccountData) : boolean {
        return this.hasRole("ADMIN", identity);
    }

    public isUser(identity: AccountData) : boolean {
        return this.hasRole("USER", identity);
    }

    public isGuest(identity: AccountData) : boolean {
        if (identity) return true;
        if (identity.roles) return true;
        if (identity.roles.length === 0) return true;
        else return false; 
    }

    public hasRole(role: string, identity: AccountData): boolean {  
        let hasRole: boolean = false;
    
        if(identity && identity.roles) {
          hasRole = identity.roles.indexOf(role) !== -1;
        }
    
        return hasRole;
      }
}
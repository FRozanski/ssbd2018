import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from "@angular/router";
import { Observable } from "rxjs/Observable";
import { AccountService } from "./account.service";
import { AuthUtilService } from "./auth-util.service";
import { AccountData } from "../model/account-data";

export class AuthGuard implements CanActivate {

    protected identity: AccountData;

    constructor (protected authUtil : AuthUtilService, protected accountService: AccountService) {
        this.accountService.getCurrentUserIdentity().subscribe((data: AccountData) => {
            this.identity = data;
        });
     }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
        return this.authUtil.hasRole(route.data.expectedRoute, this.identity);
    }

}

import { CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from "@angular/router";
import { Observable } from "rxjs/Observable";
import { AccountService } from "./account.service";
import { AuthUtilService } from "./auth-util.service";
import { AccountData } from "../model/account-data";
import { Injectable } from "@angular/core";
import {SessionService} from './session.service';

@Injectable()
export class AuthGuard implements CanActivate {

    protected identity: AccountData;

    constructor (protected authUtil : AuthUtilService, protected sessionService: SessionService, ) {
        this.sessionService.getMyIdentity().subscribe((data: AccountData) => {
            this.identity = data;
        });
     }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean {
        let b =  this.authUtil.hasRole(route.data.expectedRoute, this.identity);
        return true;
    }

}

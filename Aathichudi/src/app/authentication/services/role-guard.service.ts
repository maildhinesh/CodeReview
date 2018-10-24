import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot } from '@angular/router';
import { AuthServiceService } from './auth-service.service';

import { decode } from 'jwt-decode';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class RoleGuardService implements CanActivate {

  constructor(private authService: AuthServiceService, private _router: Router) { }

  canActivate(route: ActivatedRouteSnapshot) : boolean {
    const expectedRole = route.data.expectedRole;

    const token: string = this.authService.getToken();
    var jwtHelper = new JwtHelperService();
    const tokenPayload = jwtHelper.decodeToken(token); //decode(token);

    const roles: Array<string> = tokenPayload.roles;
    if(!this.authService.isAuthenticated() || !this.isRolePresentInToken(expectedRole, roles)) {
      return false;
    }
    return true;
  }

  private isRolePresentInToken(expectedRole: string, roles: Array<string>) : boolean {
    for(let role of roles) {
      if(role == expectedRole) {
        return true;
      }
    }
    return false;
  }
}

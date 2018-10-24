import { Injectable } from '@angular/core';
import { AuthServiceService } from './auth-service.service';
import { Router, CanActivate } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class AuthGuardService implements CanActivate {

  constructor(private authService: AuthServiceService, private _router: Router) { }

  canActivate() : boolean {
    if(!this.authService.isAuthenticated()) {
      this._router.navigate(['login']);
      return false;
    }
    return true;
  }

}

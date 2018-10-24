import { HttpClient, HttpHeaders, HttpEvent, HttpInterceptor, HttpRequest } from '@angular/common/http';

import { URLSearchParams } from '@angular/http';

import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

import { AuthConfiguration } from '../auth-configuration';

import { JWSToken, UserProfile } from '../../entity/authentication';

import {JwtHelperService} from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class AuthServiceService {

  jwtHelperService: JwtHelperService;

  constructor(private http: HttpClient, private _configuration: AuthConfiguration) { 
    this.jwtHelperService = new JwtHelperService();
  }

  public login(username: string, password: string) : Observable<JWSToken> {
    let body = new URLSearchParams();
    body.set('username', username);
    body.set('password', password);
    let options = {
      headers: new HttpHeaders().set('Content-Type', 'application/x-www-form-urlencoded')
    };
    return this.http.post<JWSToken>(this._configuration.loginURL, body.toString(), options);
  }

  public getProfile() : Observable<UserProfile> {
    return this.http.get<UserProfile>(this._configuration.profileURL);
  }

  public saveToken(token: JWSToken) {
    localStorage.setItem('token', JSON.stringify(token));
  }

  public getToken(): string {
    let token: JWSToken =  JSON.parse(localStorage.getItem('token'));
    if(token == null) {
      return null;
    } 
    if(!this.jwtHelperService.isTokenExpired(token.jws)) {
      return token.jws;
    }
    localStorage.removeItem('token');
    return null;
  }

  public isAuthenticated(): Boolean {
    const token: String = this.getToken();
    if (token != null) {
      return true;
    }
    return false;
  }

  public saveProfile(profile: UserProfile) {
    localStorage.setItem('profile', JSON.stringify(profile));
  }

  public profile() : UserProfile {
    return JSON.parse(localStorage.getItem('profile'));
  }

  public isAdmin() : Boolean {
    return this.isExpectedRolePresent("ROLE_ADMIN");
  }

  private isExpectedRolePresent(expectedRole: String) {
    const payload = this.jwtHelperService.decodeToken(this.getToken());
    for(let role of payload.roles) {
      if(role == expectedRole) {
        return true;
      }
    }
    return false;
  }

  public isUser() : Boolean {
    return this.isExpectedRolePresent("ROLE_USER");
  }

  public isTeacher() : Boolean { 
    return this.isExpectedRolePresent("ROLE_TEACHER")
  }

}

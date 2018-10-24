import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

import { Registration } from '../../entity/registration';

import { RegisterItem } from '../../registration/register';
import { HttpClient } from '@angular/common/http';
import { RegistrationConfig } from '../registration-config';
import { CreateParentLoginRequest } from '../../entity/authentication';
import { TamilSchoolResponse } from '../../entity/user-registration';

@Injectable({
  providedIn: 'root'
})
export class OldRegistrationService {

  private subjectMap: Map<String, BehaviorSubject<String>> = new Map<String, BehaviorSubject<String>>();

  private registrationSource = new BehaviorSubject<Registration>(new Registration());
  currentRegistration = this.registrationSource.asObservable();
  
  constructor(private http: HttpClient, private _config: RegistrationConfig) { }

  changeRegistration(registration: Registration) {
    this.registrationSource.next(registration);
  }

  publish(eventName: String){
    if(!this.subjectMap.has(eventName)) {
      this.subjectMap.set(eventName, new BehaviorSubject<String>(eventName));
    }
    return this.subjectMap.get(eventName).next(eventName);
  }

  on(eventName: String): Observable<String> {
    if(!this.subjectMap.has(eventName)){
      this.subjectMap.set(eventName, new BehaviorSubject<String>(eventName));
    }
    return this.subjectMap.get(eventName).asObservable();
  }

  public getRegistration(id: String) : Observable<Registration> {
    return this.http.get<Registration>(this._config.getRegistrationURL + id);
  }

  public createParentLoginRequest(request: CreateParentLoginRequest) : Observable<TamilSchoolResponse> {
    return this.http.post<TamilSchoolResponse>(this._config.createParentLoginURL, request);
  }
}

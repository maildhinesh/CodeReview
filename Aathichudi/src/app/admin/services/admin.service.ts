import { HttpClient, HttpHeaders, HttpEvent, HttpInterceptor, HttpRequest } from '@angular/common/http';

import { Injectable } from '@angular/core';
import { Observable, BehaviorSubject } from 'rxjs';
import { CreateUserRequest, TamilSchoolResponse, StudentRegistration, GetAllRegistrationsResponse } from '../../entity/user-registration';
import { Registration } from '../../entity/registration';
import { HomeConfig } from '../home-config';


@Injectable({
  providedIn: 'root'
})
export class AdminService {

  private subjectMap: Map<String, BehaviorSubject<String>> = new Map<String, BehaviorSubject<String>>();

  private registrationSource = new BehaviorSubject<Registration>(new Registration());
  currentRegistrationId = this.registrationSource.asObservable();
  config : HomeConfig;

  constructor(private http: HttpClient) { }

  public setConfig(adminConfig: HomeConfig) {
    this.config = adminConfig;
  }

  public createUser(request: CreateUserRequest) : Observable<TamilSchoolResponse>{
    return this.http.post<TamilSchoolResponse>(this.config.createRegistrationURL, request);
  }

  public getAllRegistrations() : Observable<Registration[]> {
    return this.http.get<Registration[]>(this.config.getAllRegistrationsURL);
    ;
  }

  public getRegistration(id: String) : Observable<Registration> {
    return this.http.get<Registration>(this.config.getRegistrationByIdURL + id);
  }

  changeRegistrationId(registration: Registration) {
    this.registrationSource.next(registration);
  }

  publish(eventName: String) {
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

}

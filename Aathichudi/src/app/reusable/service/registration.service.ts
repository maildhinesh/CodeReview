import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpEvent, HttpInterceptor, HttpRequest } from '@angular/common/http';

import { Observable, BehaviorSubject } from 'rxjs';
import { CreateUserRequest, TamilSchoolResponse, StudentRegistration, GetAllRegistrationsResponse } from '../../entity/user-registration';
import { HomeConfig } from '../../admin/home-config';
import { Registration } from '../../entity/registration';
import { AdminConfiguration } from '../../admin/admin-configuration';
import { CreateParentLoginRequest } from '../../entity/authentication';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  private subjectMap: Map<String, BehaviorSubject<String>> = new Map<String, BehaviorSubject<String>>();
  private registrationSource = new BehaviorSubject<Registration>(new Registration());
  currentRegistration = this.registrationSource.asObservable();
  private configSource = new BehaviorSubject<HomeConfig>(new AdminConfiguration());
  currentConfig = this.configSource.asObservable();
  config: HomeConfig;

  constructor(private http: HttpClient) { }

  setConfig(config: HomeConfig) {
    this.config = config;
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

  public createParentLoginRequest(request: CreateParentLoginRequest) : Observable<TamilSchoolResponse> {
    return this.http.post<TamilSchoolResponse>(this.config.createParentLoginURL, request);
  }

  public saveRegistration(request: Registration) : Observable<TamilSchoolResponse> {
    return this.http.post<TamilSchoolResponse>(this.config.saveRegistrationURL, request);
  }

  changeRegistration(registration: Registration) {
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

  changeConfig(config: HomeConfig) {
    this.configSource.next(config);
  }
}

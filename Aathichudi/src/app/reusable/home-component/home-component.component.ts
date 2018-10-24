import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroupDirective, NgForm, Validators, FormGroup, FormBuilder} from '@angular/forms';
import {ErrorStateMatcher} from '@angular/material/core';
import { Router, ActivatedRoute } from '@angular/router';
import { StudentRegistration, GetAllRegistrationsResponse } from '../../entity/user-registration';
import { HttpErrorResponse } from '@angular/common/http';
import { Registration } from '../../entity/registration';
import { HomeConfig } from '../../admin/home-config';
import { Observable } from 'rxjs';
import { RegistrationService } from '../service/registration.service';
import { AuthServiceService } from '../../authentication/services/auth-service.service';
import { UserConfig } from '../../user/user-config';

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

@Component({
  selector: 'app-home-component',
  templateUrl: './home-component.component.html',
  styleUrls: ['./home-component.component.css']
})
export class HomeComponentComponent implements OnInit {

  matcher = new MyErrorStateMatcher();
  viewRegistrations : Array<ViewRegistration> = new Array<ViewRegistration>();
  registrations : Map<String, Registration> = new Map<String, Registration>();
  config: HomeConfig;
  sub : Observable<HomeConfig>;
  userRole : String = '';
  constructor(private _formBuilder: FormBuilder, private _router: Router,private registrationService: RegistrationService, private authService: AuthServiceService) {
  }

  ngOnInit() {
    //this.registrationService.currentConfig.subscribe(config => this.setConfig(config));
    if(this.authService.isUser()) {
      this.setConfig(new UserConfig());
    }
  }

  setConfig(config: HomeConfig) {
    this.config = config;
    this.userRole = config.title;
    this.registrationService.setConfig(config);
    this.getAllRegistrations();
  }

  public getAllRegistrations(){
    this.registrationService.getAllRegistrations().subscribe(
      (registration: Registration[]) => this.getAllRegistrationsSuccess(registration),
      (error: HttpErrorResponse) => this.showErrorMessage(error.message)
    );
  }

  public getAllRegistrationsSuccess(registrations: Registration[]) {
    for(let registration of registrations) {
      let viewRegistration = new ViewRegistration(registration, this.config);
      this.viewRegistrations.push(viewRegistration);
      this.registrations.set(registration.id, registration);
    }
  }

  public showErrorMessage(message: string) {
    alert(message);
  }
  
  public createNewRegistration() {
    this._router.navigate(['admin-create-registration']);
  }

  public takeAction(id: string) {
    console.log(id);
    this.registrationService.changeRegistration(this.registrations.get(id));
    this.registrationService.publish('next');
    this._router.navigate([this.config.nextActionUrl]);
  }

  public isNotAdmin() {
    return !this.config.isAdmin;
  }
}

export class ViewRegistration {
  id: String;
  fullName: String;
  status: String;
  action: String;

  constructor(registration: Registration, config: HomeConfig) {
    this.id = registration.id;
    this.fullName = registration.student.lastName + ", " + registration.student.firstName;
    this.status = registration.status;
    if(config.isAdmin) {
      if(this.status == "Admin Review Pending") {
        this.action = config.reviewLabel;
      }
      else {
        this.action = config.viewLabel;
      }
    } else {
      if(this.status == "User Input pending") {
        this.action = config.reviewLabel;
      } else {
        this.action = config.viewLabel;
      }
    }
  }
}


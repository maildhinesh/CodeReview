import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroupDirective, NgForm, Validators, FormGroup, FormBuilder} from '@angular/forms';
import {ErrorStateMatcher} from '@angular/material/core';
import { Router } from '@angular/router';
import { AdminService } from '../services/admin.service';
import { StudentRegistration, GetAllRegistrationsResponse } from '../../entity/user-registration';
import { HttpErrorResponse } from '@angular/common/http';
import { Registration } from '../../entity/registration';
import { AdminConfiguration } from '../admin-configuration';
import { HomeConfig } from '../home-config';

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

@Component({
  selector: 'app-admin-home',
  templateUrl: './admin-home.component.html',
  styleUrls: ['./admin-home.component.css']
})
export class AdminHomeComponent implements OnInit {

  matcher = new MyErrorStateMatcher();
  viewRegistrations : Array<ViewRegistration> = new Array<ViewRegistration>();
  registrations : Map<String, Registration> = new Map<String, Registration>();

  config: HomeConfig;

  constructor(private _formBuilder: FormBuilder, private _router: Router, private adminService: AdminService, private adminConfig: AdminConfiguration) {
    this.adminService.setConfig(adminConfig);
   }

  ngOnInit() {
    this.getAllRegistrations();
  }

  public getAllRegistrations(){
    this.adminService.getAllRegistrations().subscribe(
      (registration: Registration[]) => this.getAllRegistrationsSuccess(registration),
      (error: HttpErrorResponse) => this.showErrorMessage(error.message)
    );
  }

  public getAllRegistrationsSuccess(registrations: Registration[]) {
    for(let registration of registrations) {
      let viewRegistration = new ViewRegistration(registration, this.adminConfig);
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
    this.adminService.changeRegistrationId(this.registrations.get(id));
    this.adminService.publish('next');
    this._router.navigate(['admin-view-registration']);
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
    if(this.status == "Admin Review Pending") {
      this.action = config.reviewLabel;
    }
    else {
      this.action = config.viewLabel;
    }
  }
}

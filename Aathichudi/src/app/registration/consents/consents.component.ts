import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroupDirective, NgForm, Validators, FormGroup, FormBuilder} from '@angular/forms';
import {ErrorStateMatcher} from '@angular/material/core';
import { Router } from '@angular/router';


import { Registration, InsuranceInfo } from '../../entity/registration';
import { MAT_CHIPS_DEFAULT_OPTIONS } from '@angular/material';

import { Register } from '../register';
import { RegistrationService } from '../../reusable/service/registration.service';
import { TamilSchoolResponse } from '../../entity/user-registration';
import { HttpErrorResponse } from '../../../../node_modules/@angular/common/http';

@Component({
  selector: 'app-consents',
  templateUrl: './consents.component.html',
  styleUrls: ['./consents.component.css']
})
export class ConsentsComponent implements OnInit, Register {

  insuranceInfo: FormGroup;
  insuranceConsent: FormGroup;
  photoConsent: FormGroup;

  registration: Registration;

  constructor(private _formBuilder: FormBuilder, private _router: Router, private service: RegistrationService) { }

  ngOnInit() {
    this.service.currentRegistration.subscribe(registration => this.registration = registration);

    if(this.registration.student.insuranceInfo == null) {
      this.registration.student.insuranceInfo = new InsuranceInfo();
    }

    this.insuranceInfo = this._formBuilder.group({
      insuranceProvider: [this.registration.student.insuranceInfo.insuranceProvider, Validators.required],
      insuranceId: [this.registration.student.insuranceInfo.insuranceId, Validators.required],
      doctor: [this.registration.student.insuranceInfo.doctor, Validators.required],
      doctorContact: [this.registration.student.insuranceInfo.doctrorContact, Validators.required]
    });

    this.insuranceConsent = this._formBuilder.group({
      insuranceConsent: [this.registration.student.insuranceConsent, Validators.required]
    });

    this.photoConsent = this._formBuilder.group({
      photoConsent : [this.registration.student.photoConsent, Validators.required]
    });
  }

  public save(isValid: Boolean) {
    if(isValid) {
      this.registration.student.insuranceInfo = this.insuranceInfo.value;
      var insuranceConsent = this.insuranceConsent.get('insuranceConsent').value;
      if(insuranceConsent != this.registration.student.insuranceConsent) {
        this.registration.student.insuranceConsent = insuranceConsent;
        this.registration.student.insuranceConsentDate = new Date();
      }
      var photoConsent = this.photoConsent.get('photoConsent').value;
      if(photoConsent != this.registration.student.photoConsent) {
        this.registration.student.photoConsent = photoConsent;
        this.registration.student.photoConsentDate = new Date();
      }
    }
    this.submitRegistration();
    //this._router.navigate(["register-success"]);
  }

  public submitRegistration() {
    this.service.saveRegistration(this.registration).subscribe(
      (response: TamilSchoolResponse) => this.submitSuccess(response),
      (error: HttpErrorResponse) => this.submitFailure(error)
    );
  }

  public submitSuccess(response: TamilSchoolResponse) {
    if(response.success) {
      this.service.changeRegistration(this.registration);
      this.service.publish('next');
    }
  }

  public submitFailure(error: HttpErrorResponse) {

  }

  public back() {
    //this._router.navigate(["register-emergency-contact"]);
    this.service.publish('prev');
  }

}

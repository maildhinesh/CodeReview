import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroupDirective, NgForm, Validators, FormGroup, FormBuilder} from '@angular/forms';
import {ErrorStateMatcher} from '@angular/material/core';
import { Router } from '@angular/router';

import { Registration } from '../../entity/registration';

import { Register } from '../register';
import { RegistrationService } from '../../reusable/service/registration.service';

@Component({
  selector: 'app-success',
  templateUrl: './success.component.html',
  styleUrls: ['./success.component.css']
})
export class SuccessComponent implements OnInit {

  oldRegistration: Registration;
  newRegistration: Registration;

  successGroup: FormGroup;

  showControls: Boolean = false;

  constructor(private _formBuilder: FormBuilder, private _router: Router, private service: RegistrationService) { }

  ngOnInit() {
    this.service.currentRegistration.subscribe( registration=> this.oldRegistration = registration);

    this.successGroup = this._formBuilder.group({
      copyStudent: [''],
      copyParents: [''],
      copyEmergencyContacts : [''],
      copyInsuranceInfo: ['']
    });

    this.observeCheckboxes();
  }

  public observeCheckboxes() {
    const copyStudent = this.successGroup.get('copyStudent');
    copyStudent.valueChanges.forEach(
      (value: Boolean) => this.copyStudentAddress(value)
    );

    const copyParents = this.successGroup.get('copyParents');
    copyParents.valueChanges.forEach(
      (value: Boolean) => this.copyParents(value)
    );

    const copyEmergencyContacts = this.successGroup.get('copyEmergencyContacts');
    copyEmergencyContacts.valueChanges.forEach(
      (value: Boolean) => this.copyEmergencyContacts(value)
    );

    const copyInsuranceInfo = this.successGroup.get('copyInsuranceInfo');
    copyInsuranceInfo.valueChanges.forEach(
      (value: Boolean) => this.copyInsuranceInfo(value)
    );
  }

  public addNewStudent() {
    this.newRegistration = new Registration();
    this.showControls = true;
  }

  public copyStudentAddress(copy: Boolean) {
    if(copy){
      this.newRegistration.student.copyAddress(this.oldRegistration.student.address);
    }
  }

  public copyParents(copy: Boolean){
    if(copy){
      this.newRegistration.student.parents[0].copy(this.oldRegistration.student.parents[0]);
      this.newRegistration.student.parents[1].copy(this.oldRegistration.student.parents[1]);
    }
  }

  public copyEmergencyContacts(copy: Boolean) {
    if(copy) {
      this.newRegistration.student.emergencyContacts[0].copy(this.oldRegistration.student.emergencyContacts[0]);
      this.newRegistration.student.emergencyContacts[1].copy(this.oldRegistration.student.emergencyContacts[1]);
    }
  }

  public copyInsuranceInfo(copy: Boolean) {
    if(copy) {
      this.newRegistration.student.insuranceInfo.copy(this.oldRegistration.student.insuranceInfo);
    }
  }

  public continue() {
    this.service.changeRegistration(this.newRegistration);
    this._router.navigate(["user-view-registration"]);
  }
}

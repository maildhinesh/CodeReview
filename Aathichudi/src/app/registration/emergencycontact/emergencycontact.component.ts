import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroupDirective, NgForm, Validators, FormGroup, FormBuilder} from '@angular/forms';
import {ErrorStateMatcher} from '@angular/material/core';
import { Router } from '@angular/router';
import { Registration, EmergencyContact } from '../../entity/registration';

import { Register } from '../register';
import { RegistrationService } from '../../reusable/service/registration.service';

@Component({
  selector: 'app-emergencycontact',
  templateUrl: './emergencycontact.component.html',
  styleUrls: ['./emergencycontact.component.css']
})
export class EmergencycontactComponent implements OnInit, Register {

  emergencyContactOneDetails: FormGroup;
  emergencyContactTwoDetails: FormGroup;

  registration: Registration;

  constructor(private _formBuilder: FormBuilder, private _router: Router, private service: RegistrationService) { }

  ngOnInit() {
    this.service.currentRegistration.subscribe(registration => this.registration = registration);
    if(this.registration.student.emergencyContacts == null) {
      this.registration.student.emergencyContacts = new Array<EmergencyContact>();
    }
    if(this.registration.student.emergencyContacts.length == 0) {
      this.registration.student.emergencyContacts.push(new EmergencyContact());
      this.registration.student.emergencyContacts.push(new EmergencyContact());
    } else if(this.registration.student.emergencyContacts.length == 1) {
      this.registration.student.emergencyContacts.push(new EmergencyContact());
    }

    this.emergencyContactOneDetails = this._formBuilder.group({
      contactName: [this.registration.student.emergencyContacts[0].contactName, Validators.required],
      relationship: [this.registration.student.emergencyContacts[0].relationship, Validators.required],
      homePhone: [this.registration.student.emergencyContacts[0].homePhone, Validators.required],
      cellPhone: [this.registration.student.emergencyContacts[0].cellPhone, Validators.required]
    });
    this.emergencyContactTwoDetails = this._formBuilder.group({
      contactName: [this.registration.student.emergencyContacts[1].contactName, Validators.required],
      relationship: [this.registration.student.emergencyContacts[1].relationship, Validators.required],
      homePhone: [this.registration.student.emergencyContacts[1].homePhone, Validators.required],
      cellPhone: [this.registration.student.emergencyContacts[1].cellPhone, Validators.required]
    });
  }

  public save(emergencyOne: EmergencyContact, emergencyTwo: EmergencyContact, isValid: Boolean) {
    if(isValid){
      this.registration.student.emergencyContacts[0] = emergencyOne;
      this.registration.student.emergencyContacts[1] = emergencyTwo;
      this.service.changeRegistration(this.registration);
      //this._router.navigate(["register-consents"]);
      this.service.publish('next');
    }
  }

  public back() {
    //this._router.navigate(["register-parents"]);
    this.service.publish('prev');
  }

}

import { Component, OnInit, Output } from '@angular/core';
import {FormControl, FormGroupDirective, NgForm, Validators, FormGroup, FormBuilder} from '@angular/forms';
import {ErrorStateMatcher} from '@angular/material/core';
import { Router } from '@angular/router';


import {schoolGrades, aathichudiGrades} from '../../entity/constants';

import { Registration, Student, Address } from '../../entity/registration';

import { Register } from '../register';
import { EventEmitter } from 'events';
import { ViewStudent } from '../../entity/user-registration';
import { RegistrationService } from '../../reusable/service/registration.service';

/** Error when invalid control is dirty, touched, or submitted. */
export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

@Component({
  selector: 'app-student',
  templateUrl: './student.component.html',
  styleUrls: ['./student.component.css']
})
export class StudentComponent implements OnInit, Register {

  phonePattern = "^[0-9]{10}$";

  studentDetails: FormGroup;
  parentsDetails: FormGroup;
  emergencyContactDetails: FormGroup;
  insuranceDetails: FormGroup;
  consents: FormGroup;

  schoolGrades = schoolGrades;
  aathichudiGrades = aathichudiGrades;

  matcher = new MyErrorStateMatcher();

  registration: Registration;

  constructor(private _formBuilder: FormBuilder, private _router: Router, private service: RegistrationService) { 
  }

  public nextClicked() {
    
    this._router.navigate(['register-parents']);
  }

  public save(model: ViewStudent, isValid: Boolean) {
    if(isValid) {
      
      this.registration.student.firstName = model.firstName;
      this.registration.student.lastName = model.lastName;
      this.registration.student.sex = model.sex;
      this.registration.student.dateOfBirth = model.dateOfBirth;
      this.registration.student.address.city = model.address.city;
      this.registration.student.address.streetName = model.address.streetName;
      this.registration.student.address.state = model.address.state;
      this.registration.student.address.zipcode = model.address.zipcode;
      this.registration.schoolGrade = model.schoolGrade;
      this.registration.aathichudiGrade = model.aathichudiGrade;
      this.registration.registrationDate = new Date();
      console.log(this.registration.student);
      this.service.changeRegistration(this.registration);
      //this._router.navigate(['register-parents']);
      this.service.publish('next');
    }
  }

  ngOnInit() {
    this.service.currentRegistration.subscribe(registration => this.registration = registration);
    if(this.registration.student.address == null) {
      this.registration.student.address = new Address();
    }
    this.studentDetails = this._formBuilder.group({
      firstName: [this.registration.student.firstName,Validators.required],
      lastName: [this.registration.student.lastName,Validators.required],
      sex: [this.registration.student.sex, Validators.required],
      dateOfBirth: [this.registration.student.dateOfBirth, Validators.required],
      schoolGrade: [this.registration.schoolGrade, Validators.required],
      aathichudiGrade: [this.registration.aathichudiGrade, Validators.required],
      address: this._formBuilder.group({
        streetName: [this.registration.student.address.streetName, Validators.required],
        city: [this.registration.student.address.city, Validators.required],
        state: [ this.registration.student.address.state, Validators.required],
        zipcode: [this.registration.student.address.zipcode, Validators.required]
      })
    });
  }

}

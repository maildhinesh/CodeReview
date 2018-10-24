import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroupDirective, NgForm, Validators, FormGroup, FormBuilder} from '@angular/forms';
import {ErrorStateMatcher} from '@angular/material/core';
import { Router } from '@angular/router';

import { Registration, Parent, Address } from '../../entity/registration';
import { MAT_CHIPS_DEFAULT_OPTIONS } from '@angular/material';

import { Register } from '../register';
import { RegistrationService } from '../../reusable/service/registration.service';

@Component({
  selector: 'app-parents',
  templateUrl: './parents.component.html',
  styleUrls: ['./parents.component.css']
})
export class ParentsComponent implements OnInit, Register {
  
  parentOneDetails: FormGroup;
  parentTwoDetails: FormGroup;

  registration: Registration;

  constructor(private _formBuilder: FormBuilder, private _router: Router, private service: RegistrationService) { }

  ngOnInit() {

    this.service.currentRegistration.subscribe(registration => this.registration = registration);
    
    if(this.registration.student.parents == null || this.registration.student.parents.length == 0) {
      this.registration.student.parents.push(new Parent());
      this.registration.student.parents.push(new Parent());
    } else if(this.registration.student.parents.length == 1){
      if(this.registration.student.parents[0].address == null) {
        this.registration.student.parents[0].address = new Address();
      }
      this.registration.student.parents.push(new Parent());
    } else {
      if(this.registration.student.parents[1].address == null) {
        this.registration.student.parents[1].address = new Address();
      }
    }

    this.parentOneDetails = this._formBuilder.group({
      firstName: [this.registration.student.parents[0].firstName, Validators.required],
      lastName: [this.registration.student.parents[0].lastName, Validators.required],
      email: [this.registration.student.parents[0].email, Validators.required],
      isPrimary: [this.registration.student.parents[0].isPrimary, Validators.required],
      homePhone: [this.registration.student.parents[0].homePhone, Validators.required],
      cellPhone: [this.registration.student.parents[0].cellPhone, Validators.required],
      address: this._formBuilder.group({
        streetName: [this.registration.student.parents[0].address.streetName, Validators.required],
        city: [this.registration.student.parents[0].address.city, Validators.required],
        state: [this.registration.student.parents[0].address.state, Validators.required],
        zipcode: [this.registration.student.parents[0].address.zipcode, Validators.required]
      })
    });

    

    this.parentTwoDetails = this._formBuilder.group({
      firstName: [this.registration.student.parents[1].firstName, Validators.required],
      lastName: [this.registration.student.parents[1].lastName, Validators.required],
      email: [this.registration.student.parents[1].email, Validators.required],
      isPrimary: [this.registration.student.parents[1].isPrimary, Validators.required],
      homePhone: [this.registration.student.parents[1].homePhone, Validators.required],
      cellPhone: [this.registration.student.parents[1].cellPhone, Validators.required],
      address: this._formBuilder.group({
        streetName: [this.registration.student.parents[1].address.streetName, Validators.required],
        city: [this.registration.student.parents[1].address.city, Validators.required],
        state: [this.registration.student.parents[1].address.state, Validators.required],
        zipcode: [this.registration.student.parents[1].address.zipcode, Validators.required]
      })
    });

    this.parentOneDetails.controls.isPrimary.setValue(true);

    this.observePrimary();

  }

  public observePrimary() {
    const isPrimaryOne = this.parentOneDetails.get('isPrimary');
    isPrimaryOne.valueChanges.forEach(
      (value: Boolean) => this.updatePrimary('ONE', value)
    );

    const isPrimaryTwo = this.parentTwoDetails.get('isPrimary');
    isPrimaryTwo.valueChanges.forEach(
      (value: Boolean) => this.updatePrimary('TWO', value)
    );
  }

  public save(parentOne: Parent, parentTwo: Parent) {
    this.registration.student.parents[0] = parentOne;
    this.registration.student.parents[1] = parentTwo;
    this.service.changeRegistration(this.registration);
    //this._router.navigate(["register-emergency-contact"]);
    this.service.publish('next');
  }

  public back(){
    //this._router.navigate(["register-student"]);
    this.service.publish('prev');
  }

  public copyStudentsAddress(parent: String) {
    if(parent == "ONE") {
      this.parentOneDetails.controls.address.setValue(this.registration.student.address);
    }
    if(parent == "TWO") {
      this.parentTwoDetails.controls.address.setValue(this.registration.student.address);
    }
  }

  public updatePrimary(parent: String, primary: Boolean) {
    if(parent == "ONE") {
      if(primary){ 
        this.parentTwoDetails.controls.isPrimary.setValue(false);
      } else {
        this.parentTwoDetails.controls.isPrimary.setValue(true);
      }
    }
    if(parent == "TWO") {
      if(primary) {
        this.parentOneDetails.controls.isPrimary.setValue(false);
      }else{
        this.parentOneDetails.controls.isPrimary.setValue(true);
      }
    }
  }

}

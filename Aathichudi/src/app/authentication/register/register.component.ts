import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroupDirective, NgForm, Validators, FormGroup, FormBuilder} from '@angular/forms';
import {ErrorStateMatcher} from '@angular/material/core';
import { Router } from '@angular/router';
import { UserRegistration, ActivateAccount } from '../../entity/user-registration';
import { Student } from '../../entity/registration';

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterUserComponent implements OnInit {

  register: Boolean = true;
  showConfirmation: Boolean = false;
  showSuccess: Boolean = false;

  userRegistrationDetails: FormGroup;
  emailFormControl = new FormControl('', [Validators.required, Validators.email]);
  firstName = new FormControl('', [Validators.required]);
  lastName = new FormControl('', [Validators.required]);
  password = new FormControl('', [Validators.required]);
  verifyPassword = new FormControl('', [Validators.required]);

  verifyStudent: FormGroup;
  studentFirstName = new FormControl('', [Validators.required]);
  studentLastName = new FormControl('', [Validators.required]);
  studentDOB = new FormControl('', [Validators.required]);
  minDate = new Date(2000,0,1);
  maxDate = new Date();
  
  pinPattern = /^\d+$/;
  activateAccount: FormGroup;
  activationPin = new FormControl('', [Validators.required, Validators.pattern(this.pinPattern)]);

  matcher = new MyErrorStateMatcher();

  constructor(private _formBuilder: FormBuilder, _router: Router) {
    
  }

  ngOnInit() {

    this.userRegistrationDetails = this._formBuilder.group({
      email: this.emailFormControl,
      firstName: this.firstName,
      lastName: this.lastName,
      password: this.password,
      verifyPassword: this.verifyPassword
    });

    this.verifyStudent = this._formBuilder.group({
      firstName: this.studentFirstName,
      lastName: this.studentLastName,
      dateOfBirth: this.studentDOB
    });

    this.activateAccount = this._formBuilder.group({
      activationPin: this.activationPin
    });

  }

  public save(user: UserRegistration, isValid: Boolean) {
    if(isValid){
      console.log(user);
      this.register = false;
      this.showConfirmation = true;
      this.showSuccess = false;
    }
  }

  public validate(student: Student, isValid: Boolean) {
    if (isValid){
      console.log(student);
      this.register = false;
      this.showConfirmation = false;
      this.showSuccess = true;
    }
  }

  public confirmPin(activateAccount: ActivateAccount, isValid: Boolean) {
    if(isValid) {
      console.log(activateAccount);
    }
  }

}

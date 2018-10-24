import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroupDirective, NgForm, Validators, FormGroup, FormBuilder} from '@angular/forms';
import {ErrorStateMatcher} from '@angular/material/core';
import { Router } from '@angular/router';

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent implements OnInit {

  matcher = new MyErrorStateMatcher();

  emailForm: FormGroup;
  pinForm: FormGroup;
  resetForm: FormGroup;

  pinPattern = /^\d+$/;
  enterEmail: Boolean = true;
  enterPin: Boolean = false;
  resetPassword: Boolean = false;

  emailFormControl: FormControl = new FormControl('', [Validators.required, Validators.email]);
  pinFormControl: FormControl = new FormControl('', [Validators.required, Validators.pattern(this.pinPattern)]);
  password: FormControl = new FormControl('', [Validators.required]);
  verifyPassword: FormControl = new FormControl('', [Validators.required]); 

  constructor(private _formBuilder: FormBuilder, private _router: Router) { }

  ngOnInit() {
    this.enterEmail = true;

    this.emailForm = this._formBuilder.group({
      emailFormControl: this.emailFormControl
    });

    this.pinForm = this._formBuilder.group({
      pinFormControl: this.pinFormControl
    });

    this.resetForm = this._formBuilder.group({
      password: this.password,
      verifyPassword: this.verifyPassword
    });
  }

  public requestPin(email: String, isValid: Boolean) {
    if(isValid) {
      this.enterEmail = false;
      this.enterPin = true;
      this.resetPassword = false;
    }
  }

  public validatePin(pin: String, isValid: Boolean) {
    if(isValid) {
      this.enterEmail = false;
      this.enterPin = false;
      this.resetPassword = true;
    }
  }

}

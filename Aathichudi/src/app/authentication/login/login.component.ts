import { Component, OnInit } from '@angular/core';import {FormControl, FormGroupDirective, NgForm, Validators, FormGroup, FormBuilder} from '@angular/forms';
import {ErrorStateMatcher} from '@angular/material/core';
import { Router } from '@angular/router';
import { AuthServiceService } from '../services/auth-service.service';
import { Authentication, JWSToken, UserProfile, UserRole } from '../../entity/authentication';
import { HttpErrorResponse } from '@angular/common/http';
import { RegistrationService } from '../../reusable/service/registration.service';
import { UserConfig } from '../../user/user-config';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginDetails: FormGroup;

  userNameValid: Boolean = true;
  passwordValid: Boolean = true;
  showErrorMessage : Boolean = true;
  serverError: String = "";

  constructor(private _formBuilder: FormBuilder, private _router: Router, private authService: AuthServiceService, private registrationService: RegistrationService) { }

  ngOnInit() {
    
    if(this.authService.isAuthenticated()) {
      this.navigateToHomePage();
    } 

    this.loginDetails = this._formBuilder.group({
      userName: ['', Validators.email],
      password: ['', Validators.required]
    });

  }

  public login(user: Authentication) {
    this.authService.login(user.userName, user.password).subscribe(
      (response: JWSToken) => this.loginSuccess(response),
      (error: HttpErrorResponse) => this.loginError(error.message)
    );
    console.log("Login clicked");
  }

  public loginSuccess(jwToken: JWSToken) {
    console.log(jwToken);
    this.authService.saveToken(jwToken);
    this.navigateToHomePage();
  }

  public loginError(message: string) {
    this.showErrorMessage = false;
    this.serverError = message;
    console.log(message);
  }

  public getProfile() {
    this.authService.getProfile().subscribe(
      (response: UserProfile) => this.navigateToHomePage(),
      (error: HttpErrorResponse) => this.loginError(error.message)
    );
  }

  public navigateToHomePage() {
    if(this.authService.isAdmin()) {
      this._router.navigate(['admin-home']);
    } else if(this.authService.isTeacher()) {
      this._router.navigate(['teacher-home']);
    } else {
      //this.registrationService.changeConfig(new UserConfig());
      //this.registrationService.publish("NEXT_CONFIG");
      this._router.navigate(['user-home']);
    }
  }

}

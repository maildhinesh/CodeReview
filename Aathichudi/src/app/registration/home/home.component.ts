import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserRegistration, CreateParentLoginRequest } from '../../entity/authentication';
import { Registration } from '../../entity/registration';
import { HttpErrorResponse } from '@angular/common/http';
import { TamilSchoolResponse } from '../../entity/user-registration';
import { RegistrationService } from '../../reusable/service/registration.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {

  userRegistration: FormGroup;
  registrationId: String = "";
  parentEmail: String = "";
  registration: Registration;
  serverError: String;

  constructor(private _formBuilder: FormBuilder, private _router: Router, private registrationService: RegistrationService, private route: ActivatedRoute) {
    this.route.params.subscribe( params => this.registrationId = params.registrationId);
   }

  ngOnInit() {

    this.userRegistration = this._formBuilder.group({
      userName: ['', Validators.email],
      password: ['', Validators.required],
      repeatPassword: ['', Validators.required]
    });
    this.getRegistration();
  }

  private getRegistration() {
    this.registrationService.getRegistration(this.registrationId).subscribe(
      registration => this.getRegistrationSuccess(registration),
      (error: HttpErrorResponse) => this.showError(error.message)
    )
  }

  private getRegistrationSuccess(registration: Registration) {
    if(registration.userCreated) {
      this._router.navigate(['login']);
    }
    this.registration = registration;
    if(this.registration.student && this.registration.student.parents && this.registration.student.parents[0].email) {
      this.parentEmail = registration.student.parents[0].email;
    }
  }

  private showError(message: String) {
    this.serverError = message;
  }

  public register(userRegistration: UserRegistration) {
    if(userRegistration.userName != this.parentEmail) {
      this.serverError = "Email Address does not match the registration details";
      return;
    }
    if(userRegistration.password != userRegistration.repeatPassword) {
      this.serverError = "Passwords don't match";
      return;
    }
    var request : CreateParentLoginRequest = new CreateParentLoginRequest();
    request.password = userRegistration.password;
    request.registrationId = this.registrationId;
    this.registrationService.createParentLoginRequest(request).subscribe(
      (response: TamilSchoolResponse) => this.createUserSuccess(response),
      (error: HttpErrorResponse) => this.showError(error.message)
    );
  }

  private createUserSuccess(response: TamilSchoolResponse) {
    if(response.success == true) {
      this._router.navigate(['login']);
    }
  }

}

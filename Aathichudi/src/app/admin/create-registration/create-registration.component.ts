import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroupDirective, NgForm, Validators, FormGroup, FormBuilder} from '@angular/forms';
import {ErrorStateMatcher} from '@angular/material/core';
import { Router } from '@angular/router';
import { CreateUserRequest, TamilSchoolResponse } from '../../entity/user-registration';
import { aathichudiGrades } from '../../entity/constants'
import { AdminService } from '../services/admin.service';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

export class MyErrorStateMatcher implements ErrorStateMatcher {
  isErrorState(control: FormControl | null, form: FormGroupDirective | NgForm | null): boolean {
    const isSubmitted = form && form.submitted;
    return !!(control && control.invalid && (control.dirty || control.touched || isSubmitted));
  }
}

@Component({
  selector: 'app-create-registration',
  templateUrl: './create-registration.component.html',
  styleUrls: ['./create-registration.component.css']
})
export class CreateRegistrationComponent implements OnInit {

  matcher = new MyErrorStateMatcher();

  registrationRequest : FormGroup;
  studentRequest: FormGroup;

  aathichudiGrades = aathichudiGrades;

  firstNameControl : FormControl = new FormControl('', [Validators.required]);
  lastNameControl: FormControl = new FormControl('', [Validators.required]);
  emailControl : FormControl = new FormControl('', [Validators.email, Validators.required]);
  
  studentFNControl : FormControl = new FormControl('', [Validators.required]);
  studentLNControl : FormControl = new FormControl('', [Validators.required]);
  aathichudiGradeControl: FormControl = new FormControl('', [Validators.required]);

  errorMessage: string;
  showErrorMesasge: boolean = true;

  constructor(private _formBuilder: FormBuilder, private _router: Router, private adminService: AdminService) { }

  ngOnInit() {

    this.studentRequest = this._formBuilder.group({
      firstName: this.studentFNControl,
      lastName: this.studentLNControl,
      aathichudiGrade: this.aathichudiGradeControl
    })

    this.registrationRequest = this._formBuilder.group({
      firstName: this.firstNameControl,
      lastName: this.lastNameControl,
      email: this.emailControl,
      student: this.studentRequest
    });

  }

  public createUser(request: CreateUserRequest, valid: Boolean ) {
    this.adminService.createUser(request).subscribe(
      (response: TamilSchoolResponse) => this.createSuccess(response),
      (error: HttpErrorResponse) => this.showError(error.message)
    );
    console.log(request);
  }

  public createSuccess(response: TamilSchoolResponse) {
    if(response.success) {
      alert("Registration added successfully");
      this._router.navigate(['admin-home']);
    } else {
      if(response.message != null) {
        this.showErrorMesasge = false;
        this.errorMessage = response.message;
      }
    }
  }

  public showError(message: string ) {
    this.showErrorMesasge = false;
    this.errorMessage = message;
  }

}

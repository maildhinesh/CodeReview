import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { HttpClientModule } from '@angular/common/http';

import { BrowserAnimationsModule } from '@angular/platform-browser/animations'
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { FlexLayoutModule } from '@angular/flex-layout';
import { RoutingModule } from './routing.module';
import { AathichudiMaterialModule } from './material.module';

import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { TokenInterceptor } from './token-interceptor';

import { AppComponent } from './app.component';

import { StudentComponent } from './registration/student/student.component';
import { ParentsComponent } from './registration/parents/parents.component';
import { EmergencycontactComponent } from './registration/emergencycontact/emergencycontact.component';
import { ConsentsComponent } from './registration/consents/consents.component';
import { SuccessComponent } from './registration/success/success.component';
import { RegisterDirective } from './registration/register.directive';
import { RegisterComponent } from './registration/register/register.component';

import { LoginComponent } from './authentication/login/login.component';
import { RegisterUserComponent } from './authentication/register/register.component';
import { CompareDirective } from './compare.directive';
import { ForgotPasswordComponent } from './authentication/forgot-password/forgot-password.component';
import { AuthServiceService } from './authentication/services/auth-service.service';
import { AdminHomeComponent } from './admin/admin-home/admin-home.component';
import { CreateRegistrationComponent } from './admin/create-registration/create-registration.component';
import { ViewRegistrationComponent } from './admin/view-registration/view-registration.component';
import { HomeComponent } from './registration/home/home.component';
import { HomeComponentComponent } from './reusable/home-component/home-component.component';
import { UserHomeComponent } from './user/user-home/user-home.component';
import { RegistrationService } from './reusable/service/registration.service';

export function jwtOptionsFactory(authService: AuthServiceService) {
  return {
    tokenGetter: () => {
      return authService.getToken();
    }
  }
}

@NgModule({
  declarations: [
    AppComponent,
    StudentComponent,
    ParentsComponent,
    EmergencycontactComponent,
    ConsentsComponent,
    SuccessComponent,
    RegisterComponent,
    RegisterDirective,
    LoginComponent,
    RegisterUserComponent,
    CompareDirective,
    ForgotPasswordComponent,
    AdminHomeComponent,
    CreateRegistrationComponent,
    ViewRegistrationComponent,
    HomeComponent,
    HomeComponentComponent,
    UserHomeComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    RoutingModule,
    AathichudiMaterialModule,
    FormsModule,
    FlexLayoutModule,
    HttpClientModule
  ],
  entryComponents: [StudentComponent, ParentsComponent, EmergencycontactComponent, ConsentsComponent, SuccessComponent],
  providers: [RegistrationService, 
              AuthServiceService,
              {
                provide: HTTP_INTERCEPTORS,
                useClass: TokenInterceptor,
                multi: true
              }
   ],
  bootstrap: [AppComponent]
})
export class AppModule { }

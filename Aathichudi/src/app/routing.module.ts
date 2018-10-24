import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {HashLocationStrategy, LocationStrategy} from '@angular/common';


import { RegisterComponent } from './registration/register/register.component';
import { LoginComponent } from './authentication/login/login.component';
import { RegisterUserComponent } from './authentication/register/register.component';
import { ForgotPasswordComponent } from './authentication/forgot-password/forgot-password.component';
import { AdminHomeComponent } from './admin/admin-home/admin-home.component';
import { CreateRegistrationComponent } from './admin/create-registration/create-registration.component';
import { ViewRegistrationComponent } from './admin/view-registration/view-registration.component';

import { AuthGuardService as AuthGuard } from './authentication/services/auth-guard.service';
import { RoleGuardService as RoleGuard } from './authentication/services/role-guard.service';
import { HomeComponent } from './registration/home/home.component';
import { HomeComponentComponent } from './reusable/home-component/home-component.component';
import { UserHomeComponent } from './user/user-home/user-home.component';

const AATHICHUDI_ROUTES : Routes = [
    {path: '', redirectTo: 'login', pathMatch: 'full'},
    {path: 'app-register', component: RegisterComponent},
    {path: 'login', component: LoginComponent},
    {path: 'register-user', component: RegisterUserComponent},
    {path: 'forgot-password', component: ForgotPasswordComponent},
    {path: 'register-student/:registrationId', component: HomeComponent},
    {path: 'admin-home', component: AdminHomeComponent, canActivate: [RoleGuard], data: { expectedRole : 'ROLE_ADMIN'} },
    {path: 'admin-create-registration', component: CreateRegistrationComponent, canActivate: [RoleGuard], data: { expectedRole : 'ROLE_ADMIN'} },
    {path: 'admin-view-registration', component: ViewRegistrationComponent, canActivate: [RoleGuard], data: { expectedRole : 'ROLE_ADMIN'} },
    {path: 'user-home', component: HomeComponentComponent, canActivate: [RoleGuard], data: { expectedRole: 'ROLE_USER'} },
    {path: 'user-view-registration', component: RegisterComponent, canActivate: [RoleGuard], data: { expectedRole: 'ROLE_USER'}}
];

@NgModule({
    exports: [RouterModule],
    imports: [
        RouterModule.forRoot(AATHICHUDI_ROUTES)
    ],
    providers: [
        {provide: LocationStrategy, useClass: HashLocationStrategy}
    ]
})
export class RoutingModule {}
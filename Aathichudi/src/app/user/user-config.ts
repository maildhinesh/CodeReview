import { HomeConfig } from "../admin/home-config";
import { Injectable } from "@angular/core";

@Injectable({
    providedIn: 'root'
})
export class UserConfig implements HomeConfig{
    public Server = "http://localhost:8080/";
    public createRegistrationURL = this.Server + "admin/createUser";
    public getAllRegistrationsURL = this.Server + "user/getRegistrations";
    public getRegistrationByIdURL = this.Server + "user/getRegistrationById/";
    public saveRegistrationURL = this.Server + "user/registration";
    public viewLabel = "View";
    public reviewLabel = "Edit";
    public nextActionUrl = "user-view-registration";
    public title = "User ";
    public isAdmin = false;
    public createParentLoginURL = this.Server + "public/createParentLogin"
}

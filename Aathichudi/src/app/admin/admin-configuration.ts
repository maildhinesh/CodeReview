import { Injectable } from "@angular/core";
import { HomeConfig } from "./home-config";


@Injectable({
    providedIn: 'root'
})
export class AdminConfiguration implements HomeConfig {
    public Server = "http://localhost:8080/";
    public createRegistrationURL = this.Server + "admin/createUser";
    public getAllRegistrationsURL = this.Server + "admin/getAllRegistrations";
    public getRegistrationByIdURL = this.Server + "admin/getRegistrationById/";
    public saveRegistrationURL = this.Server + "admin/registration";
    public viewLabel = "View";
    public reviewLabel = "Review";
    public nextActionUrl = "admin-view-registration";
    public title = "Admin ";
    public isAdmin = true;
    public createParentLoginURL = this.Server + "public/createParentLogin"
}

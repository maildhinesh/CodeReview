import { Injectable } from "@angular/core";

@Injectable({
    providedIn: 'root'
})
export class RegistrationConfig {
    public Server = "http://localhost:8080/";
    public getRegistrationURL = this.Server + "public/getRegistration/";
    public createParentLoginURL = this.Server + "public/createParentLogin"
}

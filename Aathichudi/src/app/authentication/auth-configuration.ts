import { Injectable } from "@angular/core";

@Injectable({
    providedIn: 'root'
})
export class AuthConfiguration {
    public Server = "http://localhost:8080/";
    public loginURL = this.Server + "login";
    public profileURL = this.Server + "user/profile";
}

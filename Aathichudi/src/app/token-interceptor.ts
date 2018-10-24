import { HttpInterceptor, HttpHandler, HttpEvent, HttpRequest } from '@angular/common/http';
import { AuthServiceService } from './authentication/services/auth-service.service';
import { Observable } from 'rxjs';
import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root' 
})
export class TokenInterceptor implements HttpInterceptor {

    constructor(private auth: AuthServiceService) {}

    intercept(request: HttpRequest<any>, next: HttpHandler) : Observable<HttpEvent<any>> {
        
        if(request.method == 'POST') {
            if(!request.headers.has('Content-Type')) {
                request = request.clone({headers: request.headers.set('Content-Type', 'application/json')});
            }
        }
        let token = this.auth.getToken();
        if(token != null && !request.headers.has('Authorization')) {
            request = request.clone({headers: request.headers.set('Authorization', 'Bearer ' + this.auth.getToken())});
        }
        return next.handle(request);
    }
    
}

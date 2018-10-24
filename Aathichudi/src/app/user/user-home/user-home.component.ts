import { Component, OnInit } from '@angular/core';
import { RegistrationService } from '../../reusable/service/registration.service';
import { UserConfig } from '../user-config';
import { Router } from '../../../../node_modules/@angular/router';

@Component({
  selector: 'app-user-home',
  templateUrl: './user-home.component.html',
  styleUrls: ['./user-home.component.css']
})
export class UserHomeComponent implements OnInit {

  constructor(private _router: Router, private userConfig: UserConfig, private registrationService: RegistrationService) {
    this.registrationService.setConfig(this.userConfig);
   }

  ngOnInit() {
    this._router.navigate(['reuse-home']);
  }

}

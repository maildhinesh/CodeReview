import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AdminService } from '../services/admin.service';
import { Registration } from '../../entity/registration';

@Component({
  selector: 'app-view-registration',
  templateUrl: './view-registration.component.html',
  styleUrls: ['./view-registration.component.css']
})
export class ViewRegistrationComponent implements OnInit {

  registration: Registration;

  constructor(private _router: Router, private adminService: AdminService) { }

  ngOnInit() {
    this.adminService.currentRegistrationId.subscribe(registration => this.registration = registration);
    this.populateView();
  }

  populateView() {
    
  }

}

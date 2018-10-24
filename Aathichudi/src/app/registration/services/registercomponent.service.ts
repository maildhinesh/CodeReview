import { Injectable } from '@angular/core';
import { RegisterItem } from '../register';
import { StudentComponent } from '../student/student.component';
import { ParentsComponent } from '../parents/parents.component';
import { EmergencycontactComponent } from '../emergencycontact/emergencycontact.component';
import { ConsentsComponent } from '../consents/consents.component';
import { SuccessComponent } from '../success/success.component';

@Injectable({
  providedIn: 'root'
})
export class RegistercomponentService {

  constructor() { }

  public getComponents() {
    return [
      new RegisterItem(StudentComponent),
      new RegisterItem(ParentsComponent),
      new RegisterItem(EmergencycontactComponent),
      new RegisterItem(ConsentsComponent),
      new RegisterItem(SuccessComponent)
    ]
  }
}

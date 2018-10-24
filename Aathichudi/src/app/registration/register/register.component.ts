import { Component, OnInit, OnDestroy, ComponentFactoryResolver, ViewChild } from '@angular/core';

import { Register, RegisterItem } from '../register';
import { RegisterDirective } from '../register.directive';

import { RegistercomponentService } from '../services/registercomponent.service';
import { Observable, Subscription } from 'rxjs';
import { RegistrationService } from '../../reusable/service/registration.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  currentComponentIndex = -1;
  @ViewChild(RegisterDirective) registerParent: RegisterDirective;
  registerItems: RegisterItem[];

  nextSubscription: Subscription;
  prevSubscription: Subscription;

  constructor(private service: RegistrationService, private componentService: RegistercomponentService, private componentFactoryResolver: ComponentFactoryResolver) { }

  ngOnInit() {
    this.registerItems = this.componentService.getComponents();
    this.loadNextComponent();
    this.nextSubscription = this.service.on('next').subscribe( () => this.loadNextComponent());
    this.prevSubscription = this.service.on('prev').subscribe( () => this.loadPreviousComponent() );
  }

  ngOnDestroy(){
    this.nextSubscription.unsubscribe();
    this.prevSubscription.unsubscribe();
  }

  private loadComponent(registerItem: RegisterItem){
    let componentFactory = this.componentFactoryResolver.resolveComponentFactory(registerItem.component);

    let viewContainerRef = this.registerParent.viewContainerRef;
    viewContainerRef.clear();

    let componentRef = viewContainerRef.createComponent(componentFactory);
  }
 
  public loadNextComponent() {
    this.currentComponentIndex++;
    if(this.currentComponentIndex == this.registerItems.length){
      this.currentComponentIndex = 0;
    }
    let registerItem = this.registerItems[this.currentComponentIndex];
    this.loadComponent(registerItem);
  }

  public loadPreviousComponent(){
    this.currentComponentIndex--;
    if(this.currentComponentIndex < 0){
      this.currentComponentIndex = 0;
    }
    let registerItem = this.registerItems[this.currentComponentIndex];
    this.loadComponent(registerItem);
  }
}

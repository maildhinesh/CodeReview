import { Directive, ViewContainerRef } from '@angular/core';

@Directive({
  selector: '[appRegister]'
})
export class RegisterDirective {

  constructor(public viewContainerRef: ViewContainerRef) { }

}

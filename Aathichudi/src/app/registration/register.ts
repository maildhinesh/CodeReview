import { Type } from "@angular/core";


export interface Register {
}

export class RegisterItem {
    constructor(public component: Type<any>) {}
}

import { ViewStudent } from "./user-registration";
import { ViewRegistration } from "../admin/admin-home/admin-home.component";

export class Address {
    streetName: String;
    city: String;
    state: String;
    zipcode: String;

    constructor() {
        this.streetName = "";
        this.state = "";
        this.city = "";
        this.zipcode = "";
    }

    public copy(address: Address) {
        this.streetName = address.streetName;
        this.city = address.streetName;
        this.state = address.state;
        this.zipcode = address.zipcode;
    }
}

export class Student {
    id: String;
    firstName: String;
    lastName: String;
    sex: String;
    dateOfBirth: Date;
    address: Address;
    parents: Array<Parent>;
    emergencyContacts: Array<EmergencyContact>;
    insuranceInfo: InsuranceInfo;
    insuranceConsent: Boolean;
    insuranceConsentDate: Date;
    photoConsent: Boolean;
    photoConsentDate: Date;

    constructor() {
        this.id = "";
        this.firstName = "";
        this.lastName = "";
        this.sex = "";
        this.dateOfBirth = new Date();
        this.address = new Address();
        this.parents = new Array<Parent>();
        this.emergencyContacts = new Array<EmergencyContact>();
        this.insuranceConsent = false;
        this.insuranceConsentDate = new Date();
        this.photoConsent = false;
        this.photoConsentDate = new Date();
    }

    public copyAddress(address: Address) {
        this.address.copy(address);
    }

    public copyFromView(student: ViewStudent) {
        this.firstName = student.firstName;
        this.lastName = student.lastName;
        this.dateOfBirth = student.dateOfBirth;
        this.sex = student.sex;
        this.copyAddress(student.address);
    }
}

export class Parent {
    id: String;
    firstName: String;
    lastName: String;
    email: String;
    isPrimary: Boolean;
    homePhone: String;
    cellPhone: String;
    address: Address;

    constructor() {
        this.id = "";
        this.firstName = "";
        this.lastName = "";
        this.email = "";
        this.isPrimary = false;
        this.homePhone = "";
        this.cellPhone = "";
        this.address = new Address();
    }

    public copy(parent: Parent) {
        this.id = parent.id;
        this.firstName = parent.firstName;
        this.lastName = parent.lastName;
        this.email = parent.email;
        this.isPrimary = parent.isPrimary;
        this.homePhone = parent.homePhone;
        this.cellPhone = parent.cellPhone;
        this.address.copy(parent.address);
    }
}

export class EmergencyContact {
    contactName: String;
    relationship: String;
    homePhone: String;
    cellPhone: String;

    constructor() {
        this.contactName = "";
        this.relationship = "";
        this.homePhone = "";
        this.cellPhone = "";
    }

    public copy(emergencyContact: EmergencyContact) {
        this.contactName = emergencyContact.contactName;
        this.relationship = emergencyContact.relationship;
        this.homePhone = emergencyContact.homePhone;
        this.cellPhone = emergencyContact.cellPhone;
    }
}

export class InsuranceInfo {
    insuranceProvider: String;
    insuranceId: String;
    doctor: String;
    doctrorContact: String;

    constructor() {
        this.insuranceId = "";
        this.insuranceProvider = "";
        this.doctor = "";
        this.doctrorContact = "";
    }

    public copy(insuranceInfo: InsuranceInfo){
        this.insuranceId = insuranceInfo.insuranceId;
        this.insuranceProvider = insuranceInfo.insuranceProvider;
        this.doctor = insuranceInfo.doctor;
        this.doctrorContact = insuranceInfo.doctrorContact;
    }
}

export class Registration {

    id: String;
    student: Student;
    registrationDate: Date;
    schoolGrade: String;
    aathichudiGrade: String;
    status: String;
    userCreated: Boolean;

    constructor() {
        this.id = "";
        this.student = new Student();
        this.registrationDate = new Date();
        this.schoolGrade = "";
        this.aathichudiGrade = "";
        this.status = "";
    }

}



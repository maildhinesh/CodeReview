import { Address } from "./registration";

export class UserRegistration {
    email: String;
    firstName: String;
    lastName: String;
    password: String;
    verifyPassword: String;
}

export class Student {
    firstName: String;
    lastName: String;
    dateOfBirth: Date;
    code: String;
}

export class ActivateAccount {
    activationPin: Number;
    code: String;
}

export class CreateUserRequest {
    firstName: String;
    lastName: String;
    email: String;
}

export class CreateUserStudent {
    firstName: String;
    lastName: String;
    aathichudiGrade: String;
}

export class TamilSchoolResponse {
    success: boolean;
    code: string;
    message: string;
}

export class StudentRegistration {
    id: string;
    schoolGrade: string;
    aathichudiGrade: string;
    status: string;
    student: RegistrationStudent;
}

export class RegistrationStudent {
    id: string;
    firstName: string;
    lastName: string;
}

export class GetAllRegistrationsResponse {
    success: boolean;
    registrations: StudentRegistration[];
}

export class ViewStudent {
    firstName: String;
    lastName: String;
    sex: String;
    dateOfBirth: Date;
    schoolGrade: String;
    aathichudiGrade: String;
    address: Address;
}




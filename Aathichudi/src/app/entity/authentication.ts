export class Authentication {
    userName: string;
    password: string;
}

export class JWSToken {
    jws: string;
    expires: Number;
}

export class UserRole {
    id: string;
    role: string;
    roleDescription: string;
}

export class UserProfile {
    roles: UserRole[]
}

export class UserRegistration {
    userName: String;
    password: String;
    repeatPassword: String;
}

export class CreateParentLoginRequest {
    registrationId: String;
    password: String;
}

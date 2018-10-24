import { TestBed, inject } from '@angular/core/testing';

import { OldRegistrationService } from './registration.service';

describe('RegistrationService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [OldRegistrationService]
    });
  });

  it('should be created', inject([OldRegistrationService], (service: OldRegistrationService) => {
    expect(service).toBeTruthy();
  }));
});

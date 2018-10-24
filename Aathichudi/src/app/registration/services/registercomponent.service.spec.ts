import { TestBed, inject } from '@angular/core/testing';

import { RegistercomponentService } from './registercomponent.service';

describe('RegistercomponentService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [RegistercomponentService]
    });
  });

  it('should be created', inject([RegistercomponentService], (service: RegistercomponentService) => {
    expect(service).toBeTruthy();
  }));
});

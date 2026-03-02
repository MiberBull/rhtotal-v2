import { TestBed, inject } from '@angular/core/testing';

import { DialogFormFilterService } from './dialog-form-filter.service';

describe('DialogFormFilterService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DialogFormFilterService]
    });
  });

  it('should be created', inject([DialogFormFilterService], (service: DialogFormFilterService) => {
    expect(service).toBeTruthy();
  }));
});

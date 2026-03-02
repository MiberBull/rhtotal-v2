import { TestBed, inject } from '@angular/core/testing';

import { PaysheetService } from './paysheet.service';

describe('PaysheetService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PaysheetService]
    });
  });

  it('should be created', inject([PaysheetService], (service: PaysheetService) => {
    expect(service).toBeTruthy();
  }));
});

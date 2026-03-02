import { TestBed, inject } from '@angular/core/testing';

import { DiscountTreeService } from './discount-tree.service';

describe('DiscountTreeService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DiscountTreeService]
    });
  });

  it('should be created', inject([DiscountTreeService], (service: DiscountTreeService) => {
    expect(service).toBeTruthy();
  }));
});

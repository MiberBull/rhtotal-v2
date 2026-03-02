import { TestBed, inject } from '@angular/core/testing';

import { DiscountService } from './discount.service';
import { HttpClientModule } from '@angular/common/http';

describe('DiscountService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [DiscountService],
      imports:[ HttpClientModule ]
    });
  });

  it('should be created', inject([DiscountService], (service: DiscountService) => {
    expect(service).toBeTruthy();
  }));
});

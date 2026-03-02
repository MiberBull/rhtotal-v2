import { TestBed, inject } from '@angular/core/testing';

import { BannerService } from './banner.service';
import { HttpClientModule } from '@angular/common/http';

describe('BannerService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [BannerService],
      imports:[ HttpClientModule ]
    });
  });

  it('should be created', inject([BannerService], (service: BannerService) => {
    expect(service).toBeTruthy();
  }));
});

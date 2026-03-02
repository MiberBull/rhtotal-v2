import { TestBed, inject } from '@angular/core/testing';

import { ClientmockService } from './clientmock.service';
import { HttpClientModule } from '@angular/common/http';

describe('ClientmockService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ClientmockService],
      imports:[ HttpClientModule ]
    });
  });

  it('should be created', inject([ClientmockService], (service: ClientmockService) => {
    expect(service).toBeTruthy();
  }));
});

import { TestBed, inject } from '@angular/core/testing';

import { NotificationService } from './notification.service';
import { HttpClientModule } from '@angular/common/http';

describe('NotificationService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [NotificationService],
      imports:[ HttpClientModule ]
    });
  });

  it('should be created', inject([NotificationService], (service: NotificationService) => {
    expect(service).toBeTruthy();
  }));
});

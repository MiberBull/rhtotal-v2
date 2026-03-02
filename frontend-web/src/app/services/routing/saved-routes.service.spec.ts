import { TestBed, inject } from '@angular/core/testing';

import { SavedRoutesService } from './saved-routes.service';

describe('SavedRoutesService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [SavedRoutesService]
    });
  });

  it('should be created', inject([SavedRoutesService], (service: SavedRoutesService) => {
    expect(service).toBeTruthy();
  }));
});

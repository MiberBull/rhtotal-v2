import { HttpClientModule } from '@angular/common/http';
import { TestBed, inject } from '@angular/core/testing';

import { RolService } from './rol.service';

describe('RolesService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
          RolService
        ],
      imports:[
          HttpClientModule
      ] 
    });
  });

  it('should be created', inject([RolService], (service: RolService) => {
    expect(service).toBeTruthy();
  }));
});

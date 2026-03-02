import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserFormSocialNetworksComponent } from './user-form-social-networks.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MATERIAL_COMPONENTS } from '../../../app.material';
import { ReactiveFormsModule } from '@angular/forms';

describe('UserFormSocialNetworksComponent', () => {
  let component: UserFormSocialNetworksComponent;
  let fixture: ComponentFixture<UserFormSocialNetworksComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserFormSocialNetworksComponent ],
      imports:[ReactiveFormsModule,MATERIAL_COMPONENTS,BrowserAnimationsModule]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserFormSocialNetworksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

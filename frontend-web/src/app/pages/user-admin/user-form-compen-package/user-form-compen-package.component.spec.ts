import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserFormCompenPackageComponent } from './user-form-compen-package.component';
import { MATERIAL_COMPONENTS } from '../../../app.material';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormBuilder } from '@angular/forms';

const formBuilder: FormBuilder = new FormBuilder();

xdescribe('UserFormCompenPackageComponent', () => {
  let component: UserFormCompenPackageComponent;
  let fixture: ComponentFixture<UserFormCompenPackageComponent>;

  

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserFormCompenPackageComponent ],
      imports:[MATERIAL_COMPONENTS,BrowserAnimationsModule],
      providers:[ { provide: FormBuilder, useValue: formBuilder } ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserFormCompenPackageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

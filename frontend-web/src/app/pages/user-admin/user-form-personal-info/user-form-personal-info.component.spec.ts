import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserFormPersonaleInfoComponent } from './user-form-personal-info.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MATERIAL_COMPONENTS } from '../../../app.material';
import { ReactiveFormsModule } from '@angular/forms';

describe('UserFormPiComponent', () => {
  let component: UserFormPersonaleInfoComponent;
  let fixture: ComponentFixture<UserFormPersonaleInfoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserFormPersonaleInfoComponent ],
      imports:[ReactiveFormsModule,MATERIAL_COMPONENTS,BrowserAnimationsModule]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserFormPersonaleInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should create a form', () => {
    expect( component.formInfoData.contains('curp') ).toBeTruthy();
    expect( component.formInfoData.contains('name') ).toBeTruthy();
    expect( component.formInfoData.contains('lastName') ).toBeTruthy();
    expect( component.formInfoData.contains('mLastName') ).toBeTruthy();
    expect( component.formInfoData.contains('gender') ).toBeTruthy();
    expect( component.formInfoData.contains('birthdate') ).toBeTruthy();
    expect( component.formInfoData.contains('civilEstatus') ).toBeTruthy();
    expect( component.formInfoData.contains('rfc') ).toBeTruthy();
    expect( component.formInfoData.contains('nss') ).toBeTruthy();
    expect( component.formInfoData.contains('personalEmail') ).toBeTruthy();
    expect( component.formInfoData.contains('cellPhone') ).toBeTruthy();
    expect( component.formInfoData.contains('country') ).toBeTruthy();
    expect( component.formInfoData.contains('permission') ).toBeTruthy();
    expect( component.formInfoData.contains('photography') ).toBeTruthy();
  });

});

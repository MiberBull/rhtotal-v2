import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserFormContractingDataComponent } from './user-form-contracting-data.component';
import { ReactiveFormsModule, FormBuilder } from '@angular/forms';
import { MATERIAL_COMPONENTS } from '../../../app.material';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

const formBuilder = new FormBuilder();

xdescribe('UserFormContractingDataComponent', () => {
  let component: UserFormContractingDataComponent;
  let fixture: ComponentFixture<UserFormContractingDataComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserFormContractingDataComponent ],
      imports:[ReactiveFormsModule,MATERIAL_COMPONENTS,BrowserAnimationsModule],
      providers:[ { provide: FormBuilder, useValue: formBuilder } ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserFormContractingDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

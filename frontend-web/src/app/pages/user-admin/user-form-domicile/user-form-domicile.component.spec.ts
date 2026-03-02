import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserFormDomicileComponent } from './user-form-domicile.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MATERIAL_COMPONENTS } from '../../../app.material';
import { ReactiveFormsModule, FormBuilder } from '@angular/forms';


const formBuilder = new FormBuilder();

describe('UserFormDComponent', () => {
  let component: UserFormDomicileComponent;
  let fixture: ComponentFixture<UserFormDomicileComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserFormDomicileComponent ],
      imports:[ReactiveFormsModule,MATERIAL_COMPONENTS,BrowserAnimationsModule],
      providers:[ { provide: FormBuilder, useValue: formBuilder } ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserFormDomicileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

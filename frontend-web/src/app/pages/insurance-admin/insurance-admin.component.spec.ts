import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InsuranceAdminComponent } from './insurance-admin.component';
import { ReactiveFormsModule } from '@angular/forms';
import { MATERIAL_COMPONENTS } from '../../app.material';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

describe('InsuranceAdminComponent', () => {
  let component: InsuranceAdminComponent;
  let fixture: ComponentFixture<InsuranceAdminComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InsuranceAdminComponent ],
      imports:[ ReactiveFormsModule,MATERIAL_COMPONENTS,BrowserAnimationsModule ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InsuranceAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserFormAssignmentDataComponent } from './user-form-assignment-data.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MATERIAL_COMPONENTS } from '../../../app.material';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';

xdescribe('UserFormAssignmentDataComponent', () => {
  let component: UserFormAssignmentDataComponent;
  let fixture: ComponentFixture<UserFormAssignmentDataComponent>;

beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserFormAssignmentDataComponent ],
      imports:[ReactiveFormsModule,MATERIAL_COMPONENTS,BrowserAnimationsModule,RouterTestingModule.withRoutes([])]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserFormAssignmentDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

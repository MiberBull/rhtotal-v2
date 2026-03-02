import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserFormLastJobComponent } from './user-form-last-job.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MATERIAL_COMPONENTS } from '../../../app.material';
import { ReactiveFormsModule } from '@angular/forms';

describe('UserFormLastJobComponent', () => {
  let component: UserFormLastJobComponent;
  let fixture: ComponentFixture<UserFormLastJobComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserFormLastJobComponent ],
      imports:[ReactiveFormsModule,MATERIAL_COMPONENTS,BrowserAnimationsModule]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserFormLastJobComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

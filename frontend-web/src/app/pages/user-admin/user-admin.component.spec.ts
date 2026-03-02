import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserAdminComponent } from './user-admin.component';
import { UserToolbarComponent } from '../user-toolbar/user-toolbar.component';
import { RouterTestingModule } from '@angular/router/testing';
import { MATERIAL_COMPONENTS } from '../../app.material';

describe('UserAdminComponent', () => {
  let component: UserAdminComponent;
  let fixture: ComponentFixture<UserAdminComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserAdminComponent,UserToolbarComponent ],
      imports:[ RouterTestingModule.withRoutes([]),MATERIAL_COMPONENTS]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

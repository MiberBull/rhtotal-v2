import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { UserToolbarComponent } from './user-toolbar.component';
import { MATERIAL_COMPONENTS } from '../../app.material';
import { RouterTestingModule } from '@angular/router/testing';

describe('UserToolbarComponent', () => {
  let component: UserToolbarComponent;
  let fixture: ComponentFixture<UserToolbarComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ UserToolbarComponent ],
      imports:[ MATERIAL_COMPONENTS,RouterTestingModule.withRoutes([]) ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(UserToolbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

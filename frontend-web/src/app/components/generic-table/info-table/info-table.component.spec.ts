import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InfoTableComponent } from './info-table.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MATERIAL_COMPONENTS } from '../../../app.material';
import { RouterTestingModule } from '@angular/router/testing';

describe('InfoTableComponent', () => {
  let component: InfoTableComponent;
  let fixture: ComponentFixture<InfoTableComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InfoTableComponent ],
      imports:[ BrowserAnimationsModule,MATERIAL_COMPONENTS,RouterTestingModule.withRoutes([]), ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InfoTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

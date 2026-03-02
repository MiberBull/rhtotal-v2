import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BannersFormComponent } from './banners-form.component';
import { MATERIAL_COMPONENTS } from '../../app.material';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

describe('BannersFormComponent', () => {
  let component: BannersFormComponent;
  let fixture: ComponentFixture<BannersFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BannersFormComponent ],
      imports:[ MATERIAL_COMPONENTS,ReactiveFormsModule,HttpClientModule,BrowserAnimationsModule ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BannersFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BanersAdminComponent } from './baners-admin.component';
import { BannersFormComponent } from '../banners-form/banners-form.component';
import { MATERIAL_COMPONENTS } from '../../app.material';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

describe('BanersAdminComponent', () => {
  let component: BanersAdminComponent;
  let fixture: ComponentFixture<BanersAdminComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BanersAdminComponent,BannersFormComponent ],
      imports:[ MATERIAL_COMPONENTS,ReactiveFormsModule,HttpClientModule,BrowserAnimationsModule ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BanersAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

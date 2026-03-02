import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DiscountAdminComponent } from './discount-admin.component';
import { CreateDiscountComponent } from '../create-discount/create-discount.component';
import { ReactiveFormsModule } from '@angular/forms';
import { MATERIAL_COMPONENTS } from '../../app.material';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { TreeComponent } from '../../components/tree/tree.component';

describe('DiscountAdminComponent', () => {
  let component: DiscountAdminComponent;
  let fixture: ComponentFixture<DiscountAdminComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ 
        DiscountAdminComponent,
        CreateDiscountComponent,
        TreeComponent ],
      imports:[ ReactiveFormsModule,MATERIAL_COMPONENTS,HttpClientModule,BrowserAnimationsModule ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DiscountAdminComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

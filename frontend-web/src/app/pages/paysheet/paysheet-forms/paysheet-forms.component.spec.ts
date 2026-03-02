import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PaysheetFormsComponent } from './paysheet-forms.component';

describe('PaysheetFormsComponent', () => {
  let component: PaysheetFormsComponent;
  let fixture: ComponentFixture<PaysheetFormsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PaysheetFormsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PaysheetFormsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

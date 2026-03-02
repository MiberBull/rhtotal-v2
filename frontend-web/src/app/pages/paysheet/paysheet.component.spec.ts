import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PaysheetComponent } from './paysheet.component';

describe('PaysheetComponent', () => {
  let component: PaysheetComponent;
  let fixture: ComponentFixture<PaysheetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PaysheetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PaysheetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

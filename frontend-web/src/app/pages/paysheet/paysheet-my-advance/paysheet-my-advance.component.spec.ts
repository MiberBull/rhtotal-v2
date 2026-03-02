import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PaysheetMyAdvanceComponent } from './paysheet-my-advance.component';

describe('PaysheetMyAdvanceComponent', () => {
  let component: PaysheetMyAdvanceComponent;
  let fixture: ComponentFixture<PaysheetMyAdvanceComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PaysheetMyAdvanceComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PaysheetMyAdvanceComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

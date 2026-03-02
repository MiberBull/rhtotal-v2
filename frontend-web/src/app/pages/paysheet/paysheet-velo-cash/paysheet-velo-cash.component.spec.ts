import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PaysheetVeloCashComponent } from './paysheet-velo-cash.component';

describe('PaysheetVeloCashComponent', () => {
  let component: PaysheetVeloCashComponent;
  let fixture: ComponentFixture<PaysheetVeloCashComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PaysheetVeloCashComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PaysheetVeloCashComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

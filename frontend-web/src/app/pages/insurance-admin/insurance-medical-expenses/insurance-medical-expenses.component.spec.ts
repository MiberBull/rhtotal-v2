import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InsuranceMedicalExpensesComponent } from './insurance-medical-expenses.component';

describe('InsuranceMedicalExpensesComponent', () => {
  let component: InsuranceMedicalExpensesComponent;
  let fixture: ComponentFixture<InsuranceMedicalExpensesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InsuranceMedicalExpensesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InsuranceMedicalExpensesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

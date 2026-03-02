import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PlanCoverageComponent } from './plan-coverage.component';

describe('PlanCoverageComponent', () => {
  let component: PlanCoverageComponent;
  let fixture: ComponentFixture<PlanCoverageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PlanCoverageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PlanCoverageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

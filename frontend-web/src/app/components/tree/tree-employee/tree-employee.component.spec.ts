import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TreeEmployeeComponent } from './tree-employee.component';

describe('TreeEmployeeComponent', () => {
  let component: TreeEmployeeComponent;
  let fixture: ComponentFixture<TreeEmployeeComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TreeEmployeeComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TreeEmployeeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

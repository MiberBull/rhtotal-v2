import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogFormsFilterComponent } from './dialog-forms-filter.component';

describe('DialogFormsFilterComponent', () => {
  let component: DialogFormsFilterComponent;
  let fixture: ComponentFixture<DialogFormsFilterComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DialogFormsFilterComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogFormsFilterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

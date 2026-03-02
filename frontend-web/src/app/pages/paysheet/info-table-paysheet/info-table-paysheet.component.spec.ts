import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InfoTablePaysheetComponent } from './info-table-paysheet.component';

describe('InfoTablePaysheetComponent', () => {
  let component: InfoTablePaysheetComponent;
  let fixture: ComponentFixture<InfoTablePaysheetComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InfoTablePaysheetComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InfoTablePaysheetComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

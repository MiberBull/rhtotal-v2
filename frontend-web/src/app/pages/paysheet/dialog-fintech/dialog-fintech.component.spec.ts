import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogFintechComponent } from './dialog-fintech.component';

describe('DialogFintechComponent', () => {
  let component: DialogFintechComponent;
  let fixture: ComponentFixture<DialogFintechComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DialogFintechComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogFintechComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

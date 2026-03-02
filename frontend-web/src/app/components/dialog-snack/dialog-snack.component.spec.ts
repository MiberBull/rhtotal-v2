import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogSnackComponent } from './dialog-snack.component';

describe('DialogSnackComponent', () => {
  let component: DialogSnackComponent;
  let fixture: ComponentFixture<DialogSnackComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DialogSnackComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogSnackComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

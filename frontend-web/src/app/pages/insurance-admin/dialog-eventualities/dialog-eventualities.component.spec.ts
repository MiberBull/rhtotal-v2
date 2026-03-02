import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogEventualitiesComponent } from './dialog-eventualities.component';

describe('DialogEventualitiesComponent', () => {
  let component: DialogEventualitiesComponent;
  let fixture: ComponentFixture<DialogEventualitiesComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DialogEventualitiesComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogEventualitiesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

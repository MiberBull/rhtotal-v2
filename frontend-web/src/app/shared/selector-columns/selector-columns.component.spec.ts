import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SelectorColumnsComponent } from './selector-columns.component';
import { DragulaModule, DragulaService } from 'ng2-dragula';
import { MATERIAL_COMPONENTS } from '../../app.material';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';

describe('SelectorColumnsComponent', () => {
  let component: SelectorColumnsComponent;
  let fixture: ComponentFixture<SelectorColumnsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SelectorColumnsComponent ],
      imports:[ DragulaModule,MATERIAL_COMPONENTS ],
      providers:[{provide: MatDialogRef,useValue: {} }, { provide: MAT_DIALOG_DATA,useValue: {}  },DragulaService]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SelectorColumnsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

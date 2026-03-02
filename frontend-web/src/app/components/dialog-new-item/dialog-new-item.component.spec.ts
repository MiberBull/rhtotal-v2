import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogNewItemComponent } from './dialog-new-item.component';
import { MATERIAL_COMPONENTS } from '../../app.material';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

describe('DialogNewItemComponent', () => {
  let component: DialogNewItemComponent;
  let fixture: ComponentFixture<DialogNewItemComponent>;

  class Dialog {
    title:string;
    placeholder:string;
  }
  
  const data = new Dialog();
  data.title = 'Titulo'
  data.placeholder = 'Placeholder'
  
  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DialogNewItemComponent ],
      imports:[ 
        MATERIAL_COMPONENTS,
        CommonModule,
        FormsModule,
        BrowserAnimationsModule
      ],
      providers:[
        { provide: MatDialogRef, useValue: {} }, 
        { provide: MAT_DIALOG_DATA, useValue: data }]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DialogNewItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

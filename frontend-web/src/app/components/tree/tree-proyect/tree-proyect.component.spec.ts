import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TreeProyectComponent } from './tree-proyect.component';

describe('TreeProyectComponent', () => {
  let component: TreeProyectComponent;
  let fixture: ComponentFixture<TreeProyectComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TreeProyectComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TreeProyectComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

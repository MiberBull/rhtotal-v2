import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { TreeClientComponent } from './tree-client.component';

describe('TreeClientComponent', () => {
  let component: TreeClientComponent;
  let fixture: ComponentFixture<TreeClientComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ TreeClientComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(TreeClientComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});

import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DiscountComponent } from './discount.component';
import { GenericTableComponent } from '../../components/generic-table/generic-table.component';
import { InfoTableComponent } from '../../components/generic-table/info-table/info-table.component';
import { TablePaginatorComponent } from '../../components/generic-table/table-paginator/table-paginator.component';
import { MATERIAL_COMPONENTS } from '../../app.material';
import { RouterTestingModule } from '@angular/router/testing';
import { HttpClientModule } from '@angular/common/http';
import { TreeComponent } from '../../components/tree/tree.component';

describe('DiscountComponent', () => {
  let component: DiscountComponent;
  let fixture: ComponentFixture<DiscountComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ 
        DiscountComponent,
        GenericTableComponent,
        InfoTableComponent,
        TablePaginatorComponent,
        TreeComponent ],
      imports:[ MATERIAL_COMPONENTS,RouterTestingModule.withRoutes([]),HttpClientModule ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DiscountComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
